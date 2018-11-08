package kr.co.treegames.tagfeed.task.main

import kr.co.treegames.tagfeed.data.source.AccountRepository
import kr.co.treegames.tagfeed.data.source.SharedPreferencesRepository
import kr.co.treegames.tagfeed.data.source.UserRepository
import java.util.*
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description :
 */
class MainPresenter(private val preferences: SharedPreferencesRepository,
                    private val accountRepository: AccountRepository,
                    private val userRepository: UserRepository,
                    val view: MainContract.View)
    : MainContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
        view.showMessage(preferences.getString("test_key") ?: "test_key is null")
    }
    override fun signOut() {
        accountRepository.signOut()
        view.startAccountActivity()
    }
    override fun sendEmail(title: String, contents: String, receiver: String, sender: String, passwordOfSender: String) {
        val props = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.port"] = "587"

        // 비밀번호 인증으로 세션 생성
        val session = Session.getInstance(props,
                object: javax.mail.Authenticator() {
                    override  fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(sender, passwordOfSender)
                    }
                })

        // 메시지 객체 만들기
        val message = MimeMessage(session)
        message.setFrom(InternetAddress(sender))
        // 수신자 설정, 여러명으로도 가능
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(receiver))
        message.subject = title
        message.setText(contents)


//        // 파일을 담기 위한 Multipart 생성
//        val multipart = MimeMultipart()
//        val messageBodyPart = MimeBodyPart()
//        val source = FileDataSource(filePath)
//
//        messageBodyPart.dataHandler = DataHandler(source)
//        messageBodyPart.fileName = fileName
//        multipart.addBodyPart(messageBodyPart)
//
//        // 메시지에 파일 담고
//        message.setContent(multipart)

        // 전송
        Thread { Transport.send(message) }.start()
    }
}