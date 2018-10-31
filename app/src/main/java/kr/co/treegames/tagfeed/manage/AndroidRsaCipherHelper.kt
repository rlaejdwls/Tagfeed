package kr.co.treegames.tagfeed.manage

import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import kr.co.treegames.core.manage.Logger
import kr.co.treegames.tagfeed.App
import java.math.BigInteger
import java.security.GeneralSecurityException
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.spec.RSAKeyGenParameterSpec
import java.security.spec.RSAKeyGenParameterSpec.F4
import java.util.Locale
import java.util.Calendar
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal

/**
 * Created by Hwang on 2018-10-25.
 *
 * Description : https://hyperconnect.github.io/2018/06/03/android-secure-sharedpref-howto.html
 * https://proandroiddev.com/secure-data-in-android-encryption-in-android-part-2-991a89e55a23
 */
object AndroidRsaCipherHelper {
    /** All inputs are must be shorter than 2048 bits(256 bytes) */
    private const val KEY_LENGTH_BIT = 2048

    // Let's think about this problem in 2043
    private const val VALIDITY_YEARS = 25

    private const val KEY_PROVIDER_NAME = "AndroidKeyStore"
    private const val CIPHER_ALGORITHM =
            "${KeyProperties.KEY_ALGORITHM_RSA}/" +
                    "${KeyProperties.BLOCK_MODE_ECB}/" +
                    KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1

    private lateinit var keyEntry: KeyStore.Entry

    // Private only backing field
    @Suppress("ObjectPropertyName")
    private var _isSupported = false

    val isSupported: Boolean
        get() = _isSupported

    internal fun init(applicationContext: Context) {
        if (isSupported) {
            Logger.w("Already initialised - Do not attempt to initialise this twice")
            return
        }

        val alias = "${applicationContext.packageName}.rsakeypairs"
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }

        val result: Boolean
        result = if (keyStore.containsAlias(alias)) {
            true
        } else {
            Logger.v("No keypair for %s, creating a new one", alias)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && initAndroidM(alias)) {
                true
            } else {
                initAndroidL(alias)
            }
        }

        this.keyEntry = keyStore.getEntry(alias, null)
        _isSupported = result
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initAndroidM(alias: String): Boolean {
        try {
            with(KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEY_PROVIDER_NAME)) {
                val spec = KeyGenParameterSpec.Builder(alias,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                        .setAlgorithmParameterSpec(RSAKeyGenParameterSpec(KEY_LENGTH_BIT, F4))
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                        .setDigests(KeyProperties.DIGEST_SHA512,
                                KeyProperties.DIGEST_SHA384,
                                KeyProperties.DIGEST_SHA256)
                        /*
                         * Setting true only permit the private key to be used if the user authenticated
                         * within the last five minutes.
                         */
                        .setUserAuthenticationRequired(false)
                        .build()
                initialize(spec)
                generateKeyPair()
            }
            Logger.i(String.format("Random keypair with %s/%s/%s is created.", KeyProperties.KEY_ALGORITHM_RSA,
                    KeyProperties.BLOCK_MODE_CBC, KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1))

            return true
        } catch (e: GeneralSecurityException) {
            /*
             * Nonsense, but some devices manufactured by developing countries have actual problem
             * Consider using JCE substitutes like Spongy castle(Bouncy castle for android)
             */
            Logger.e("It seems that this device does not support latest algorithm!!", e)

            return false
        }
    }

    private fun initAndroidL(alias: String): Boolean {
//        val start = Calendar.getInstance()
//        val end = Calendar.getInstance()
//        end.add(Calendar.YEAR, 1)
//        val spec = KeyPairGeneratorSpec.Builder(App.get())
//                .setAlias(alias)
//                .setSubject(X500Principal("CN=Your Company ," +
//                        " O=Your Organization" +
//                        " C=Your Coountry"))
//                .setSerialNumber(BigInteger.ONE)
//                .setStartDate(start.time)
//                .setEndDate(end.time)
//                .build()
//        val generator = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA, "AndroidKeyStore")
//
//        generator.initialize(spec)
//        generator.generateKeyPair()
        try {
            with(KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEY_PROVIDER_NAME)) {
                val start = Calendar.getInstance(Locale.ENGLISH)
                val end = Calendar.getInstance(Locale.ENGLISH).apply { add(Calendar.YEAR, VALIDITY_YEARS) }
                val spec = KeyPairGeneratorSpec.Builder(App.get())
                        .setKeySize(KEY_LENGTH_BIT)
                        .setAlias(alias)
                        .setSubject(X500Principal("CN=francescojo.github.com, OU=Android dev, O=Francesco Jo, L=Chiyoda, ST=Tokyo, C=JP"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.time)
                        .setEndDate(end.time)
                        .build()
                initialize(spec)
                generateKeyPair()
            }
            Logger.i(String.format("Random keypair with %s is created, which is old fashioned",
                    KeyProperties.KEY_ALGORITHM_RSA))

            return true
        } catch (e: GeneralSecurityException) {
            Logger.e("It seems that this device does not support encryption!!", e)

            return false
        }
    }

    /**
     * Beware that input must be shorter than 256 bytes. The length limit of plainText could be dramatically
     * shorter than 256 letters in certain character encoding, such as UTF-8.
     */
    fun encrypt(plainText: String): String {
        if (!_isSupported) {
            return plainText
        }

        val cipher = Cipher.getInstance(CIPHER_ALGORITHM).apply {
            init(Cipher.ENCRYPT_MODE, (keyEntry as KeyStore.PrivateKeyEntry).certificate.publicKey)
        }
        val bytes = plainText.toByteArray(Charsets.UTF_8)
        val encryptedBytes = cipher.doFinal(bytes)
        val base64EncryptedBytes = Base64.encode(encryptedBytes, Base64.DEFAULT)

        return String(base64EncryptedBytes)
    }
    fun decrypt(base64EncryptedCipherText: String): String {
        if (!_isSupported) {
            return base64EncryptedCipherText
        }

        val cipher = Cipher.getInstance(CIPHER_ALGORITHM).apply {
            init(Cipher.DECRYPT_MODE, (keyEntry as KeyStore.PrivateKeyEntry).privateKey)
        }
        val base64EncryptedBytes = base64EncryptedCipherText.toByteArray(Charsets.UTF_8)
        val encryptedBytes = Base64.decode(base64EncryptedBytes, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        return String(decryptedBytes)
    }
}