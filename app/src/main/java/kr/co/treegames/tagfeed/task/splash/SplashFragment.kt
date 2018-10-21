package kr.co.treegames.tagfeed.task.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_splash.*
import kr.co.treegames.tagfeed.task.DefaultFragment
import kr.co.treegames.tagfeed.task.account.AccountActivity
import kr.co.treegames.tagfeed.task.main.MainActivity

/**
 * Created by Hwang on 2018-08-31.
 *
 * Description :
 */
class SplashFragment: DefaultFragment(), SplashContract.View {
    override lateinit var presenter: SplashContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.automatic()
    }
    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(isShow: Boolean) {
//        if (isShow) {
//            anim_logo.playAnimation()
//        } else {
//            anim_logo.cancelAnimation()
//        }
//        Handler(Looper.getMainLooper()).post { progress_bar.visibility = if(isShow) View.VISIBLE else View.GONE }
    }
    override fun showMessage(message: String) {
        Handler(Looper.getMainLooper()).post { Toast.makeText(activity, message, Toast.LENGTH_LONG).show() }
    }
    override fun startMainActivity() {
        startActivity(Intent(activity, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
    override fun startAccountActivity() {
        startActivity(Intent(activity, AccountActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}