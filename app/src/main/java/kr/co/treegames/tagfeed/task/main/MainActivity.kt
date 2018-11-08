package kr.co.treegames.tagfeed.task.main

import android.os.Bundle
import android.view.Menu
import kr.co.treegames.tagfeed.Injection
import kr.co.treegames.tagfeed.R
import kr.co.treegames.tagfeed.task.DefaultActivity
import kr.co.treegames.tagfeed.task.replaceFragmentInActivity
import kr.co.treegames.tagfeed.task.setToolbar

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description :
 */
class MainActivity: DefaultActivity() {
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolbar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.app_name)
        }

        val fragment: MainFragment = supportFragmentManager.findFragmentById(R.id.content) as MainFragment?
                ?: Injection.provideFragment(MainFragment::class.java).also {
                    replaceFragmentInActivity(it, R.id.content)
                }

        presenter = MainPresenter(
                Injection.provideSharedPreferences(applicationContext),
                Injection.provideAccountRepository(),
                Injection.provideUserRepository(),
                fragment
        )
    }
}