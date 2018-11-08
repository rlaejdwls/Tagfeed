package kr.co.treegames.tagfeed.task.main

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import kotlinx.android.synthetic.main.fragment_main.*
import kr.co.treegames.core.manage.Logger
import kr.co.treegames.tagfeed.App
import kr.co.treegames.tagfeed.R
import kr.co.treegames.tagfeed.data.model.Const
import kr.co.treegames.tagfeed.data.model.Key
import kr.co.treegames.tagfeed.manage.AndroidRsaCipherHelper
import kr.co.treegames.tagfeed.task.DefaultFragment
import kr.co.treegames.tagfeed.task.account.AccountActivity
import java.net.URLDecoder
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import javax.crypto.Cipher

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description : View
 */
class MainFragment: DefaultFragment(), MainContract.View {
    override lateinit var presenter: MainContract.Presenter

    private val onClick = fun(view: View) {
        when(view.id) {
            R.id.btn_ml_kit_ocr -> {
                progress_bar.visibility = View.VISIBLE
                img_test_data.visibility = View.VISIBLE
                img_test_data.setImageDrawable(resources.getDrawable(R.drawable.test_img_ml_kit_ocr_example_en, null))
                val data: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_img_ml_kit_ocr_example_en)
                val image: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(data)
                val recognizer: FirebaseVisionTextRecognizer = FirebaseVision.getInstance()
                        .onDeviceTextRecognizer

                recognizer.processImage(image).addOnSuccessListener {
                    txt_test_data.text = it.text
                    progress_bar.visibility = View.GONE
                }.addOnFailureListener {
                    progress_bar.visibility = View.GONE
                }
            }
            R.id.btn_ml_kit_landmark -> {
                progress_bar.visibility = View.VISIBLE
                img_test_data.visibility = View.VISIBLE
                img_test_data.setImageDrawable(resources.getDrawable(R.drawable.test_img_ml_kit_landmark_example_tokyo_sukaitsuri, null))
                val data: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_img_ml_kit_landmark_example_tokyo_sukaitsuri)
                val image: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(data)
                val detector: FirebaseVisionCloudLandmarkDetector = FirebaseVision.getInstance()
                        .visionCloudLandmarkDetector
                detector.detectInImage(image)
                        .addOnSuccessListener {
                            progress_bar.visibility = View.GONE
                            val builder = StringBuilder()
                            it.forEach { landmark ->
                                builder.append("---------------------------------------------------").append("\n")
                                builder.append("Landmark Name:").append(landmark.landmark).append("\n")
                                builder.append("Landmark Entity ID:").append(landmark.entityId).append("\n")
                                builder.append("Landmark Coordinates:")
                                landmark.locations.forEach { location ->
                                    builder.append(location.latitude).append(",").append(location.longitude).append("\n")
                                }
                                builder.append("---------------------------------------------------").append("\n")
                            }
                            txt_test_data.text = builder.toString()
                        }.addOnFailureListener {
                            progress_bar.visibility = View.GONE
                        }
            }
            R.id.btn_test_data_clear -> {
                img_test_data.visibility = View.GONE
                img_test_data.setImageDrawable(null)
                txt_test_data.text = ""
            }
            R.id.btn_start_camera -> {
            }
            R.id.btn_detect_shape -> {
            }
            R.id.btn_test -> {
                //메일 전송 테스트
//                presenter.sendEmail("메일 테스트 제목", "메일 테스트 내용", "", "", "")

                //RSA 암복호화 예제
//                activity?.run {
//                    AndroidRsaCipherHelper.init(applicationContext)
//                    if (AndroidRsaCipherHelper.isSupported) {
//                        val encryptString = AndroidRsaCipherHelper.encrypt("안녕하세요.")
//                        Logger.d("encrypt:$encryptString")
//                        Logger.d("decrypt:${AndroidRsaCipherHelper.decrypt(encryptString)}")
//                    }
//                }

                //Firestore 테이블 생성(?)
//                val db = FirebaseFirestore.getInstance()
//                val manage: MutableMap<String, Any> = HashMap()
//                manage["sender_email"] = "develop.duck@gmail.com"
//                manage["sender_password"] = "test1q2w3e"
//
//                db.collection("tb_manage")
//                        .add(manage)
//                        .addOnSuccessListener {
//                            Logger.d("DocumentSnapshot added with ID: ${it.id}")
//                        }
//                        .addOnFailureListener { e ->
//                            Logger.e("Error adding document", e)
//                        }
            }
        }
    }
//    private fun decryptRSAToString(encryptedBase64: String, privateKey: String): String {
//        var decryptedString = ""
//        try {
//            val keyFac = KeyFactory.getInstance("RSA")
//            val keySpec = PKCS8EncodedKeySpec(Base64.decode(privateKey.trim { it <= ' ' }.toByteArray(), Base64.DEFAULT))
//            val key = keyFac.generatePrivate(keySpec)
//
//            // get an RSA cipher object and print the provider
//            val cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING")
//            // encrypt the plain text using the public key
//            cipher.init(Cipher.DECRYPT_MODE, key)
//
//            val encryptedBytes = Base64.decode(encryptedBase64, Base64.DEFAULT)
//            val decryptedBytes = cipher.doFinal(encryptedBytes)
//            decryptedString = String(decryptedBytes)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return decryptedString
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        btn_ml_kit_ocr.setOnClickListener(onClick)
        btn_ml_kit_landmark.setOnClickListener(onClick)
        btn_test_data_clear.setOnClickListener(onClick)
        btn_start_camera.setOnClickListener(onClick)
        btn_detect_shape.setOnClickListener(onClick)
        btn_test.setOnClickListener(onClick)
    }
    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showMessage(message: String) {
        activity?.run { Handler(Looper.getMainLooper()).post { Toast.makeText(this, message, Toast.LENGTH_LONG).show() } }
    }
    override fun startAccountActivity() {
        startActivity(Intent(activity, AccountActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.run {
            when(itemId) {
                android.R.id.home -> presenter.signOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}