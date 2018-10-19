package kr.co.treegames.tagfeed.extension

import android.view.View
import android.view.ViewTreeObserver
import androidx.viewpager.widget.ViewPager

/**
 * Created by Hwang on 2018-10-19.
 *
 * Description : 뷰(View) 관련 확장 함수들
 */
fun View.onViewCreated(action: (view: View) -> Unit) {
    with(viewTreeObserver) {
        addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                this@onViewCreated.viewTreeObserver.removeOnGlobalLayoutListener(this)
                action(this@onViewCreated)
            }
        })
    }
}
fun ViewPager.onPageScrolled(action: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)): ViewPager.OnPageChangeListener {
    return object: ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            action(position, positionOffset, positionOffsetPixels)
        }
        override fun onPageSelected(position: Int) {
        }
    }.apply {
        addOnPageChangeListener(this)
    }
}