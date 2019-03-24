package com.chernysh.smarthome.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.chernysh.smarthome.R
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.TemperatureHumidityViewState
import kotlinx.android.synthetic.main.layout_element.view.*

/**
 * Created by Andrii Chernysh on 3/24/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class ElectronicElementView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null,
                                                      defStyleAttr: Int = 0, defStyleRes: Int = 0) :
        FrameLayout(context, attributeSet, defStyleAttr, defStyleRes) {
    private var IsElementEnabled: Boolean = false

    init {
        val view = View.inflate(getContext(), R.layout.layout_element, null)
        addView(view)

        clElementContainer.setOnClickListener {

        }
    }

    fun render(booleanViewState: BooleanViewState) {
        when (booleanViewState) {
            is BooleanViewState.LoadingState -> renderLoading()
            is BooleanViewState.DataState -> renderData(booleanViewState.data)
            is BooleanViewState.ErrorState -> renderError(booleanViewState.error)
            is BooleanViewState.ConnectivityErrorState -> renderConnectivityError()
        }
    }

    private fun renderLoading() {
        pbElementStateLoading.visibility = View.VISIBLE
        tvElementState.visibility = View.GONE
        switchElement.isEnabled = false
    }

    private fun renderData(isEnabled: Boolean) {
        switchElement.isEnabled = true
        pbElementStateLoading.visibility = View.GONE
        tvElementState.visibility = View.VISIBLE
        tvElementState.text = getStateString(isEnabled)
        this.IsElementEnabled = isEnabled
    }

    private fun getStateString(isEnabled: Boolean) = if (isEnabled) {
        context.getString(R.string.state_enabled)
    } else {
        context.getString(R.string.state_disabled)
    }

    private fun renderError(throwable: Throwable) {
        val state = getStateString(IsElementEnabled)
        tvElementState.text = "${context.getString(R.string.server_error)} ($state)"
        switchElement.isEnabled = true
        switchElement.isChecked = IsElementEnabled

        pbElementStateLoading.visibility = View.GONE
        tvElementState.visibility = View.VISIBLE
    }

    private fun renderConnectivityError() {
        val state = getStateString(IsElementEnabled)
        tvElementState.text = "${context.getString(R.string.network_error)} ($state)"
        switchElement.isEnabled = true
        switchElement.isChecked = IsElementEnabled

        pbElementStateLoading.visibility = View.GONE
        tvElementState.visibility = View.VISIBLE
    }
}