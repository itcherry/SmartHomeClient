package com.chernysh.smarthome.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.chernysh.smarthome.R
import com.chernysh.smarthome.databinding.LayoutTemperatureHumidityBinding
import com.chernysh.smarthome.domain.model.TemperatureHumidityData
import com.chernysh.smarthome.domain.model.TemperatureHumidityViewState

/**
 * Created by Andrii Chernysh on 3/23/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class TemperatureHumidityCard @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
        FrameLayout(context, attributeSet, defStyleAttr, defStyleRes) {
    private var binding: LayoutTemperatureHumidityBinding

    init {
        binding = LayoutTemperatureHumidityBinding.inflate(LayoutInflater.from(context), this, true);
    }

    fun render(temperatureHumidityViewState: TemperatureHumidityViewState) {
        when (temperatureHumidityViewState) {
            is TemperatureHumidityViewState.NoDataState -> renderLoading()
            is TemperatureHumidityViewState.DataState -> renderData(temperatureHumidityViewState.data)
            is TemperatureHumidityViewState.ErrorState -> renderError(temperatureHumidityViewState.error)
            is TemperatureHumidityViewState.SocketConnectedState -> renderSocketConnected()
            is TemperatureHumidityViewState.SocketDisconnectedState -> renderSocketDisconnected()
            is TemperatureHumidityViewState.SocketReconnectingState -> renderSocketReconnecting()
            else -> {
                // TODO check if all good here
            }
        }
    }

    private fun renderLoading() {
        binding.groupError.visibility = View.GONE
        binding.groupLoading.visibility = View.VISIBLE
        binding.groupTemperatureHumidityData.visibility = View.GONE
    }

    private fun renderData(temperatureHumidityData: TemperatureHumidityData) {
        binding.groupError.visibility = View.GONE
        binding.groupLoading.visibility = View.GONE
        binding.groupTemperatureHumidityData.visibility = View.VISIBLE

        binding.tvCurrentTemperatureValue.text = context.getString(R.string.temperature_text, temperatureHumidityData.temperature)
        binding.tvCurrentHumidityValue.text = context.getString(R.string.humidity_text, temperatureHumidityData.humidity)
    }

    private fun renderError(throwable: Throwable){
        binding.groupError.visibility = View.GONE
        binding.groupLoading.visibility = View.GONE
        binding.groupTemperatureHumidityData.visibility = View.VISIBLE

        binding.tvError.text = context.getString(R.string.temperature_humidity_generic_error) + throwable.message
    }

    private fun renderSocketConnected() {
        binding.tvSocketError.visibility = View.GONE
    }

    private fun renderSocketDisconnected() {
        binding.tvSocketError.visibility = View.VISIBLE
        binding.tvSocketError.text = context.getString(R.string.temperature_humidity_socket_disconnected)
    }

    private fun renderSocketReconnecting() {
        binding.tvSocketError.visibility = View.VISIBLE
        binding.tvSocketError.text = context.getString(R.string.temperature_humidity_socket_reconnecting)
    }
}