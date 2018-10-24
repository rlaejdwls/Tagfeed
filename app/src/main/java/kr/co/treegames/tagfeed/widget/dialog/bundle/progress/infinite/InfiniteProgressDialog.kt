package kr.co.treegames.tagfeed.widget.dialog.bundle.progress.infinite

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import com.airbnb.lottie.LottieDrawable
import kotlinx.android.synthetic.main.dialog_infinite_progress_bar.*
import kr.co.treegames.tagfeed.R

/**
 * Created by Hwang on 2018-10-10.
 *
 * Description :
 */
class InfiniteProgressDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_infinite_progress_bar)
        window?.run {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        anim_progress.repeatCount = LottieDrawable.INFINITE
        anim_progress.playAnimation()

        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setOnDismissListener {
            anim_progress.cancelAnimation()
        }
    }
}