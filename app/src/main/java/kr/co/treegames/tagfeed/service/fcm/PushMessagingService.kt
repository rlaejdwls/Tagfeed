package kr.co.treegames.tagfeed.service.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kr.co.treegames.core.manage.Logger

/**
 * Created by Hwang on 2018-09-03.
 *
 * Description : 파이어베이스를 이용해서 푸시 알림을 받는 서비스 클래스
 */
class PushMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String?) {
        super.onNewToken(token)
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Logger.d(remoteMessage?.data.toString())
    }
}