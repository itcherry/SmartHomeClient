package com.chernysh.smarthome.presentation

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.TouchDelegate
import android.view.View
import com.chernysh.smarthome.R
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.AbsoluteLayout
import com.chernysh.smarthome.utils.TouchDelegateComposite
import com.chernysh.smarthome.utils.dpToPx
import com.chernysh.smarthome.utils.expandClickArea
import kotlinx.android.synthetic.main.activity_start.*


/**
 * Created by Andrii Chernysh on 2019-05-04
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        initButtons()
    }

    private fun initButtons() {
        val viewTreeObserver = flatPlanView.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    flatPlanView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val xStart = 32.dpToPx(this@StartActivity)
                    val xEnd = flatPlanView.measuredWidth - 32.dpToPx(this@StartActivity)
                    val yStart = 100.dpToPx(this@StartActivity)
                    val yEnd = flatPlanView.measuredHeight - 100.dpToPx(this@StartActivity)

                    val flatWidth = xEnd - xStart
                    val flatHeight = yEnd - yStart

                    initKitchenButton(xStart, yStart, flatHeight)
                    initLivingRoomButton(xStart, flatWidth, yStart, flatHeight)
                    initBedroomButton(xEnd, flatWidth, yStart, flatHeight)
                    initCorridorButton(xStart, flatWidth, yStart, flatHeight)
                }
            })
        }
    }

    private fun initCorridorButton(xStart: Int, flatWidth: Int, yStart: Int, flatHeight: Int) {
        btnCorridor.layoutParams = (btnCorridor.layoutParams as AbsoluteLayout.LayoutParams).apply {
            x = xStart + (flatWidth / 5.8f).toInt()
            y = yStart + (4.6f * flatHeight / 7).toInt()
        }
        btnCorridor.expandClickArea(alFlatPlan, (flatHeight / 6.7f).toInt(), (flatHeight / 6.7f).toInt(), flatWidth / 5, 12.dpToPx(this@StartActivity))
    }

    private fun initBedroomButton(xEnd: Int, flatWidth: Int, yStart: Int, flatHeight: Int) {
        btnBedroom.layoutParams = (btnBedroom.layoutParams as AbsoluteLayout.LayoutParams).apply {
            x = xEnd - (flatWidth / 4)
            y = yStart + (2.7f * flatHeight / 6).toInt()
        }
        btnBedroom.expandClickArea(alFlatPlan, (flatHeight / 7f).toInt(), (flatHeight / 7f).toInt(), 20.dpToPx(this@StartActivity), 20.dpToPx(this@StartActivity))
    }

    private fun initLivingRoomButton(xStart: Int, flatWidth: Int, yStart: Int, flatHeight: Int) {
        btnLivingRoom.layoutParams = (btnLivingRoom.layoutParams as AbsoluteLayout.LayoutParams).apply {
            x = xStart + (flatWidth / 2.3f).toInt()
            y = yStart + (flatHeight / 3.5f).toInt()
        }
        btnLivingRoom.expandClickArea(alFlatPlan, (flatHeight / 3.5f).toInt(), (flatHeight / 3.5f).toInt(), 12.dpToPx(this@StartActivity), 16.dpToPx(this@StartActivity))
    }

    private fun initKitchenButton(xStart: Int, yStart: Int, flatHeight: Int) {
        btnKitchen.layoutParams = (btnKitchen.layoutParams as AbsoluteLayout.LayoutParams).apply {
            x = xStart + 18.dpToPx(this@StartActivity)
            y = yStart + flatHeight / 5
        }
        btnKitchen.expandClickArea(alFlatPlan, flatHeight / 5, flatHeight / 8, 18.dpToPx(this@StartActivity), 18.dpToPx(this@StartActivity))
    }
}