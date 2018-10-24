package kr.co.treegames.tagfeed.task.account.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by Hwang on 2018-10-18.
 *
 * Description : 로그인과 회원가입 뷰 페이저의 아답터
 */
class AccountPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val fragmentTitleList: MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }
    override fun getCount(): Int {
        return fragmentList.size
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }
    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}