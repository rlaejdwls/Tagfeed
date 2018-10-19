package kr.co.treegames.tagfeed.task.splash

import android.os.Bundle
import android.view.WindowManager
import kr.co.treegames.tagfeed.Injection
import kr.co.treegames.tagfeed.R
import kr.co.treegames.tagfeed.task.DefaultActivity
import kr.co.treegames.tagfeed.task.replaceFragmentInActivity

/**
 * Created by Hwang on 2018-08-31.
 *
 * Description :
 */

class SplashActivity: DefaultActivity() {
    private lateinit var presenter: SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        val fragment = supportFragmentManager.findFragmentById(R.id.content) as SplashFragment?
                ?: Injection.provideFragment(SplashFragment::class.java).also {
                    replaceFragmentInActivity(it, R.id.content)
                }

        presenter = SplashPresenter(
                Injection.provideSharedPreferences(applicationContext),
                Injection.provideAccountRepository(),
                fragment
        )
    }
}