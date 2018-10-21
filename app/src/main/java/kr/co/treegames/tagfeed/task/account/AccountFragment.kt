package kr.co.treegames.tagfeed.task.account

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_account.*
import kr.co.treegames.tagfeed.App
import kr.co.treegames.tagfeed.Injection
import kr.co.treegames.tagfeed.R
import kr.co.treegames.tagfeed.data.model.Account
import kr.co.treegames.tagfeed.extension.onPageScrolled
import kr.co.treegames.tagfeed.extension.onViewCreated
import kr.co.treegames.tagfeed.task.DefaultFragment
import kr.co.treegames.tagfeed.task.account.adapter.AccountPagerAdapter
import kr.co.treegames.tagfeed.task.main.MainActivity
import kr.co.treegames.tagfeed.widget.dialog.bundle.progress.solving.InfiniteProgressUIHandler

/**
 * Created by Hwang on 2018-09-03.
 *
 * Description : 로그인, 회원 가입 관련 뷰
 */
class AccountFragment : DefaultFragment(), AccountContract.View {
    override lateinit var presenter: AccountContract.Presenter
    private val minimumOffset = 0.4f

    class TabInfo(val tab: TextView) {
        var tabSize = 0
        var preTabSize = 0
    }
    private lateinit var tabs: Array<TabInfo>
    private lateinit var listener: ViewPager.OnPageChangeListener
    private lateinit var progress: InfiniteProgressUIHandler

    private lateinit var signInView: AccountContract.View.SignInView
    private lateinit var signUpView: AccountContract.View.SignUpView

    private val onClick = fun(view: View) {
        when(view.id) {
            R.id.btn_action -> {
                when(pager.currentItem) {
                    0 -> {
                        val verification = signInView.verification()
                        if (!verification.first) {
                            Toast.makeText(context, verification.second, Toast.LENGTH_SHORT).show()
                            return
                        }
                        presenter.signIn(Account(signInView.getEmail(), signInView.getPassword()))
                    }
                    1 -> {
                        val verification = signUpView.verification()
                        if (!verification.first) {
                            Toast.makeText(context, verification.second, Toast.LENGTH_SHORT).show()
                            return
                        }
                        presenter.signUp(Account(signUpView.getEmail(), signUpView.getPassword()))
                    }
                }
            }
            R.id.btn_google_sign_in -> {
                Toast.makeText(context, signInView.getEmail(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //초기화
        context?.apply {
            progress = InfiniteProgressUIHandler(this)
        }
        tabs = arrayOf(AccountFragment.TabInfo(txt_sign_in), TabInfo(txt_sign_up))

        //이벤트
        btn_action.setOnClickListener(onClick)
        btn_google_sign_in.setOnClickListener(onClick)

        for (i in 0..(tabs.size - 1)) {
            tabs[i].tab.onViewCreated { tabs[i].tabSize = it.width + (it.layoutParams as LinearLayout.LayoutParams).rightMargin }
            tabs[i].tab.setOnClickListener { pager.setCurrentItem(i, true) }
        }
        pager.onViewCreated {
            //탭 이동을 위한 더미 뷰
            val dummy: View = TextView(context)
            dummy.layoutParams = LinearLayout.LayoutParams(it.width - tabs[tabs.size - 1].tabSize, LinearLayout.LayoutParams.WRAP_CONTENT)
            dummy.visibility = View.INVISIBLE
            layout_tabs.addView(dummy)
            //탭 이전 사이즈 계산
            for (i in 0..(tabs.size - 1)) {
                var preMenuSize = 0
                for (cnt in 0..(i - 1)) {
                    preMenuSize += tabs[cnt].tabSize
                }
                tabs[i].preTabSize = preMenuSize
            }
        }
        //서브 뷰 추가
        fragmentManager?.run {
            val adapter = AccountPagerAdapter(this).apply {
                addFragment(Injection.provideFragment(SignInFragment::class.java).apply { signInView = this }, getString(R.string.title_sign_in))
                addFragment(Injection.provideFragment(SignUpFragment::class.java).apply { signUpView = this }, getString(R.string.title_sign_up))
            }
            pager.adapter = adapter
        }
        listener = pager.onPageScrolled { position, positionOffset, positionOffsetPixels ->
            //탭 글자색 변경
            tabs[position].tab.setTextColor(Color.argb((255 * maxOf(1.0f - positionOffset, minimumOffset)).toInt(), 255, 87, 87))
            if (tabs.size - 1 > position) {
                tabs[position + 1].tab.setTextColor(Color.argb((255 * maxOf(positionOffset, minimumOffset)).toInt(), 255, 87, 87))
            }
            //탭 자동 스크롤
            layout_scroll_tab.scrollX = tabs[position].preTabSize + (positionOffsetPixels * (tabs[position].tabSize.toFloat() / pager.width.toFloat())).toInt()
            //배경 자동 스크롤
            img_background.scrollX = (App.get().getScreenWidth() * position) + (positionOffsetPixels * (App.get().getScreenWidth() / pager.width.toFloat())).toInt()
        }
        //탭 스크롤 이벤트
//        layout_scroll_tab.viewTreeObserver.addOnScrollChangedListener {
//        }
        pager.setPageTransformer(true) { page, position ->
            page.alpha = Math.abs(Math.abs(position) - 1)
        }
    }
    override fun onResume() {
        super.onResume()
        presenter.start()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        pager.removeOnPageChangeListener(listener)
    }

    override fun setLoadingIndicator(isShow: Boolean) {
        progress.handleMessage(Message().apply {
            what = if (isShow) InfiniteProgressUIHandler.Status.SHOW.ordinal else InfiniteProgressUIHandler.Status.DISMISS.ordinal
        })
    }
    override fun showMessage(message: String) {
        Handler(Looper.getMainLooper()).post { Toast.makeText(activity, message, Toast.LENGTH_LONG).show() }
    }
    override fun startMainActivity() {
        startActivity(Intent(activity, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}