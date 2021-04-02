package com.chernysh.smarthome.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.chernysh.smarthome.R
import com.chernysh.smarthome.domain.model.TemperatureData
import com.chernysh.smarthome.domain.model.TemperatureHumidityViewState
import kotlinx.android.synthetic.main.layout_temperature_humidity.view.*

/**
 * Created by Andrii Chernysh on 3/23/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class TemperatureCard @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
        FrameLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    init {
        val view = View.inflate(getContext(), R.layout.layout_temperature_humidity, null)
        addView(view)
    }

    fun render(temperatureHumidityViewState: TemperatureHumidityViewState) {
        when (temperatureHumidityViewState) {
            is TemperatureHumidityViewState.NoDataState -> renderLoading()
            is TemperatureHumidityViewState.DataState -> renderData(temperatureHumidityViewState.data)
            is TemperatureHumidityViewState.ErrorState -> renderError(temperatureHumidityViewState.error)
            is TemperatureHumidityViewState.SocketConnectedState -> renderSocketConnected()
            is TemperatureHumidityViewState.SocketDisconnectedState -> renderSocketDisconnected()
            is TemperatureHumidityViewState.SocketReconnectingState -> renderSocketReconnecting()
        }
    }

    private fun renderLoading() {
        groupError.visibility = View.GONE
        groupLoading.visibility = View.VISIBLE
        groupTemperatureHumidityData.visibility = View.GONE
    }

    private fun renderData(temperatureData: TemperatureData) {
        groupError.visibility = View.GONE
        groupLoading.visibility = View.GONE
        groupTemperatureHumidityData.visibility = View.VISIBLE

        tvCurrentTemperatureValue.text = context.getString(R.string.temperature_text, temperatureData.temperature)
    }

    private fun renderError(throwable: Throwable){
        groupError.visibility = View.GONE
        groupLoading.visibility = View.GONE
        groupTemperatureHumidityData.visibility = View.VISIBLE

        tvError.text = context.getString(R.string.temperature_humidity_generic_error) + throwable.message
    }

    private fun renderSocketConnected() {
        tvSocketError.visibility = View.GONE
    }

    private fun renderSocketDisconnected() {
        tvSocketError.visibility = View.VISIBLE
        tvSocketError.text = context.getString(R.string.temperature_humidity_socket_disconnected)
    }

    private fun renderSocketReconnecting() {
        tvSocketError.visibility = View.VISIBLE
        tvSocketError.text = context.getString(R.string.temperature_humidity_socket_reconnecting)
    }
}