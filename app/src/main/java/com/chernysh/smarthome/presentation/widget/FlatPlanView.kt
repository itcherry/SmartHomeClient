package com.chernysh.smarthome.presentation.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.chernysh.smarthome.R
import com.chernysh.smarthome.utils.dpToPx

/**
 * Created by Andrii Chernysh on 2019-05-04
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class FlatPlanView @kotlin.jvm.JvmOverloads constructor(
        context: Context, attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0, defStyleRes: Int = 0
): View(context, attributeSet, defStyleAttr, defStyleRes){
    private val linesPaint: Paint = Paint()

    init {
        linesPaint.apply {
            strokeWidth = 1.dpToPx(context).toFloat()
            color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val xStart = 32.dpToPx(context)
        val xEnd = measuredWidth - 32.dpToPx(context)
        val yStart = 64.dpToPx(context)
        val yEnd = measuredHeight + 64.dpToPx(context)


    }
}