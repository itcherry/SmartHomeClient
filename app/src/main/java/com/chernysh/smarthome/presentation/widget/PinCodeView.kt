package com.chernysh.smarthome.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.chernysh.smarthome.R
import com.chernysh.smarthome.presentation.login.PIN_CODE_LENGTH
import com.chernysh.smarthome.utils.dpToPx

/**
 * Don't want to do it as library, so will hardcode all needed data.
 *
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class PinCodeView @JvmOverloads constructor(
  context: Context, attributeSet: AttributeSet? = null,
  defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr, defStyleRes) {
  init {
    orientation = HORIZONTAL
    gravity = Gravity.CENTER

    addCircles()
  }

  private fun addCircles() {
    for (i in 0 until PIN_CODE_LENGTH) {
      addView(View(context).apply {
        layoutParams = getCircleLayoutParams()
        background = AppCompatResources.getDrawable(context, R.drawable.pin_code_outer_bg)
      })
    }
  }

  private fun getCircleLayoutParams() =
    LayoutParams(56.dpToPx(context), 56.dpToPx(context))
      .apply {
        marginStart = 4.dpToPx(context)
        marginEnd = 4.dpToPx(context)
      }

  fun setText(text: String) {
    val length = if (text.length > PIN_CODE_LENGTH) PIN_CODE_LENGTH else text.length

    unfillNotNeededCircles(length)
    fillActiveCirclesWithColor(length)
  }

  private fun unfillNotNeededCircles(length: Int) {
      for (i in 0 until PIN_CODE_LENGTH) {
        getChildAt(i).background = AppCompatResources.getDrawable(context, R.drawable.pin_code_outer_bg)
      }
  }

  private fun fillActiveCirclesWithColor(length: Int) {
    for (i in 0 until length) {
      getChildAt(i).background = AppCompatResources.getDrawable(context, R.drawable.pin_code_inner_bg_filled)
    }
  }

  fun renderError() {
    for (i in 0 until childCount) {
      getChildAt(i).background = AppCompatResources.getDrawable(context, R.drawable.pin_code_inner_bg_error)
    }
  }

  fun renderSuccess() {
    isEnabled = true

    for (i in 0 until childCount) {
      getChildAt(i).background = AppCompatResources.getDrawable(context, R.drawable.pin_code_inner_bg_success)
    }
  }

  fun clear() {
    for (i in 0 until childCount) {
      getChildAt(i).background = AppCompatResources.getDrawable(context, R.drawable.pin_code_outer_bg)
    }
  }

  override fun setEnabled(enabled: Boolean) {
    super.setEnabled(enabled)

    if (enabled) {
      for (i in 0 until childCount) {
        getChildAt(i).alpha = 1.0f
      }
    } else {
      for (i in 0 until childCount) {
        getChildAt(i).alpha = 0.6f
      }
    }
  }
}