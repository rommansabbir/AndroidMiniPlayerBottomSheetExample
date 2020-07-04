package com.rommansabbir.androidminiplayerbottomsheetexample

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rommansabbir.animationx.Zoom
import com.rommansabbir.animationx.animationXZoom
import kotlinx.android.synthetic.main.content_mini_player.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    var isExpanded: Boolean = false
    private lateinit var bottomSheetReference: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomSheetReference = BottomSheetBehavior.from(mini_player)
        bottomSheetReference.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        bottomSheetReference.state = BottomSheetBehavior.STATE_COLLAPSED
                        miniPlayerVisible()
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        miniPlayerNonVisible()
                        isExpanded = true
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        miniPlayerVisible()
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {

                    }
                }
            }
        })

        mini_player_layout.setOnClickListener {
            bottomSheetReference.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun miniPlayerNonVisible() {
        mini_player_layout.animationXZoom(Zoom.ZOOM_OUT, 100)
        CoroutineScope(Dispatchers.IO + Job()).launch {
            delay(100)
            CoroutineScope(Dispatchers.Main).launch {
                mini_player_layout.visibility = View.GONE
            }
        }
    }

    private fun miniPlayerVisible() {
        mini_player_layout.visibility = View.VISIBLE
        mini_player_layout.animationXZoom(Zoom.ZOOM_IN, 100)
        isExpanded = false
    }

    override fun onResume() {
        super.onResume()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isExpanded) {
                    bottomSheetReference.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    finish()
                }
            }
        })
    }
}