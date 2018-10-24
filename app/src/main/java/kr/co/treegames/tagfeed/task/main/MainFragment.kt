package kr.co.treegames.tagfeed.task.main

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import kotlinx.android.synthetic.main.fragment_main.*
import kr.co.treegames.core.manage.Logger
import kr.co.treegames.tagfeed.R
import kr.co.treegames.tagfeed.task.DefaultFragment
import kr.co.treegames.tagfeed.task.account.AccountActivity

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
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        btn_ml_kit_ocr.setOnClickListener(onClick)
        btn_ml_kit_landmark.setOnClickListener(onClick)
        btn_test_data_clear.setOnClickListener(onClick)
        btn_start_camera.setOnClickListener(onClick)
        btn_detect_shape.setOnClickListener(onClick)
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