package com.chernysh.smarthome.presentation.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.chernysh.smarthome.R
import com.chernysh.smarthome.utils.dpToPx

/**
 * Created by Andrii Chernysh on 2019-05-04
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class FlatPlanView @kotlin.jvm.JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0, defStyleRes: Int = 0
) : View(context, attributeSet, defStyleAttr, defStyleRes) {

    private val outerWallsPaint: Paint = Paint()
    private val outerWindowsPaint: Paint = Paint()
    private val innerWallsPaint: Paint = Paint()
    private val thinWallPaint: Paint = Paint()
    private val adjustmentOuter = 4.dpToPx(context)
    private val adjustmentInner = 2.dpToPx(context)

    init {
        outerWallsPaint.apply {
            strokeWidth = 8.dpToPx(context).toFloat()
            color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
        }
        outerWindowsPaint.apply {
            strokeWidth = 6.dpToPx(context).toFloat()
            color = ContextCompat.getColor(context, R.color.colorAccent)
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
        }
        innerWallsPaint.apply {
            strokeWidth = 4.dpToPx(context).toFloat()
            color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
        }
        thinWallPaint.apply {
            strokeWidth = 2.dpToPx(context).toFloat()
            color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val xStart = 32.dpToPx(context).toFloat()
        val xEnd = measuredWidth - 32.dpToPx(context).toFloat()
        val yStart = 100.dpToPx(context).toFloat()
        val yEnd = measuredHeight - 100.dpToPx(context).toFloat()

        val flatWidth = xEnd - xStart
        val flatHeight = yEnd - yStart

        drawOuterWindows(canvas, xStart, yStart, flatWidth, xEnd, flatHeight)
        drawInnerWalls(canvas, xStart, flatWidth, yStart, flatHeight, yEnd, xEnd)
        drawOuterWalls(canvas, xStart, yStart, yEnd, flatHeight, flatWidth, xEnd)
    }

    private fun drawInnerWalls(
        canvas: Canvas,
        xStart: Float,
        flatWidth: Float,
        yStart: Float,
        flatHeight: Float,
        yEnd: Float,
        xEnd: Float
    ) {
        canvas.drawLine(
            xStart + flatWidth / 2.7f,
            yStart + flatHeight / 7,
            xStart + flatWidth / 2.7f,
            yStart + 3.1f * flatHeight / 7,
            innerWallsPaint
        )
        canvas.drawLine(
            xStart,
            yStart + 3.1f * flatHeight / 7 - 32.dpToPx(context),
            xStart + flatWidth / 5,
            yStart + 3.1f * flatHeight / 7 - 32.dpToPx(context),
            innerWallsPaint
        )
        canvas.drawLine(
            xStart + flatWidth / 5,
            yStart + 3.1f * flatHeight / 7 - 32.dpToPx(context),
            xStart + flatWidth / 5,
            yStart + 3.1f * flatHeight / 7,
            innerWallsPaint
        )
        canvas.drawLine(
            xStart + flatWidth / 5,
            yStart + 3.1f * flatHeight / 7,
            xStart + flatWidth / 2.7f,
            yStart + 3.1f * flatHeight / 7,
            innerWallsPaint
        )
        canvas.drawLine(
            xStart + flatWidth / 5,
            yStart + 3.1f * flatHeight / 7,
            xStart + flatWidth / 5,
            yEnd - flatHeight / 9,
            innerWallsPaint
        )
        canvas.drawLine(
            xStart,
            yStart + 3.9f * flatHeight / 7,
            xStart + flatWidth / 5,
            yStart + 3.9f * flatHeight / 7,
            innerWallsPaint
        )
        canvas.drawLine(
            xEnd - (flatWidth / 4),
            yStart + (1.7f * flatHeight / 6),
            xEnd - (flatWidth / 4),
            yStart + 3.9f * flatHeight / 7,
            innerWallsPaint
        )
        canvas.drawLine(
            xEnd - (flatWidth / 4),
            (yStart + 3.9f * flatHeight / 7) - 8.dpToPx(context),
            xStart + flatWidth / 1.7f,
            (yStart + 3.9f * flatHeight / 7) - 8.dpToPx(context),
            innerWallsPaint
        )
        canvas.drawLine(
            xEnd - (flatWidth / 4),
            yEnd - (2.5f * flatHeight / 9),
            xEnd - (flatWidth / 4),
            yEnd - (2.5f * flatHeight / 9) - 24.dpToPx(context),
            innerWallsPaint
        )
        canvas.drawLine(
            xStart + flatWidth / 2.8f + 16.dpToPx(context),
            yEnd - (2.5f * flatHeight / 9),
            xStart + flatWidth / 2.8f + 16.dpToPx(context),
            yEnd - (2.5f * flatHeight / 9) - 24.dpToPx(context),
            innerWallsPaint
        )

        canvas.drawLine(
            xStart + flatWidth / 2.8f + 16.dpToPx(context),
            yEnd - (2.5f * flatHeight / 9) - 24.dpToPx(context),
            xEnd,
            yEnd - (2.5f * flatHeight / 9) - 24.dpToPx(context),
            thinWallPaint
        )
    }

    private fun drawOuterWalls(
        canvas: Canvas,
        xStart: Float,
        yStart: Float,
        yEnd: Float,
        flatHeight: Float,
        flatWidth: Float,
        xEnd: Float
    ) {
        canvas.drawLine(xStart - adjustmentOuter, yStart, xStart, yEnd - flatHeight / 9 + adjustmentOuter, outerWallsPaint)
        canvas.drawLine(xStart, yEnd - flatHeight / 9, xStart + flatWidth / 5 + adjustmentOuter, yEnd - flatHeight / 9, outerWallsPaint)
        canvas.drawLine(xStart + flatWidth / 5, yEnd - flatHeight / 9, xStart + flatWidth / 5, yEnd + adjustmentOuter, outerWallsPaint)
        canvas.drawLine(
            xStart + flatWidth / 5,
            yEnd,
            xStart + flatWidth / 5 + 24.dpToPx(context),
            yEnd,
            outerWallsPaint
        )
        canvas.drawLine(
            xStart + flatWidth / 2.8f,
            yEnd,
            xStart + flatWidth / 2.8f + 16.dpToPx(context) + adjustmentOuter,
            yEnd,
            outerWallsPaint
        )
        canvas.drawLine(
            xStart + flatWidth / 2.8f + 16.dpToPx(context),
            yEnd,
            xStart + flatWidth / 2.8f + 16.dpToPx(context),
            yEnd - (2.5f * flatHeight / 9)- adjustmentOuter,
            outerWallsPaint
        )
        canvas.drawLine(
            xStart + flatWidth / 2.8f + 16.dpToPx(context),
            yEnd - (2.5f * flatHeight / 9),
            xEnd + adjustmentOuter,
            yEnd - (2.5f * flatHeight / 9),
            outerWallsPaint
        )
        canvas.drawLine(xEnd, yEnd - (2.5f * flatHeight / 9), xEnd, yStart + (1.7f * flatHeight / 6) - adjustmentOuter, outerWallsPaint)
        canvas.drawLine(
            xEnd,
            yStart + (1.7f * flatHeight / 6),
            xEnd - (flatWidth / 4) - adjustmentOuter,
            yStart + (1.7f * flatHeight / 6),
            outerWallsPaint
        )
        canvas.drawLine(
            xEnd - (flatWidth / 4),
            yStart + (1.7f * flatHeight / 6),
            xEnd - (flatWidth / 4),
            yStart + flatHeight / 7 - adjustmentOuter,
            outerWallsPaint
        )
        canvas.drawLine(
            xEnd - (flatWidth / 4f),
            yStart + flatHeight / 7,
            xEnd - (flatWidth / 4f) - (flatWidth / 7.5f) - adjustmentOuter,
            yStart + flatHeight / 7,
            outerWallsPaint
        )
        canvas.drawLine(
            xStart,
            yStart + flatHeight / 7,
            xStart + flatWidth / 2.7f,
            yStart + flatHeight / 7,
            outerWallsPaint
        )
    }

    private fun drawOuterWindows(
        canvas: Canvas,
        xStart: Float,
        yStart: Float,
        flatWidth: Float,
        xEnd: Float,
        flatHeight: Float
    ) {
        canvas.drawLine(xStart, yStart, xStart + flatWidth / 1.8f, yStart, outerWindowsPaint)
        canvas.drawLine(
            xStart + flatWidth / 1.8f,
            yStart,
            xEnd - (flatWidth / 4f) - (flatWidth / 7.5f),
            yStart + flatHeight / 7,
            outerWindowsPaint
        )
        canvas.drawLine(
            xStart + flatWidth / 2.4f,
            yStart,
            xStart + flatWidth / 2.7f,
            yStart + flatHeight / 7,
            outerWindowsPaint
        )
        canvas.drawLine(
            xEnd - (flatWidth / 4f),
            yStart + flatHeight / 7,
            xEnd - (flatWidth / 9f),
            yStart + flatHeight / 7,
            outerWindowsPaint
        )
        canvas.drawLine(
            xEnd - (flatWidth / 4f),
            yStart + flatHeight / 7,
            xEnd - (flatWidth / 9f),
            yStart + flatHeight / 7,
            outerWindowsPaint
        )
        canvas.drawLine(
            xEnd - (flatWidth / 9f),
            yStart + flatHeight / 7,
            xEnd - 16.dpToPx(context),
            yStart + (1.7f * flatHeight / 6),
            outerWindowsPaint
        )
    }
}