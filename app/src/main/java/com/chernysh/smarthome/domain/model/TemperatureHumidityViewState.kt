package com.chernysh.smarthome.domain.model

/**
 * ViewState from MVI that describes temperature and humidity states
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
sealed class TemperatureHumidityViewState {
    object NoDataState : TemperatureHumidityViewState()
    data class DataState(val data: TemperatureData) : TemperatureHumidityViewState()
    data class ErrorState(val error: Throwable) : TemperatureHumidityViewState()
    object SocketConnectedState : TemperatureHumidityViewState()
    object SocketDisconnectedState : TemperatureHumidityViewState()
    object SocketReconnectingState : TemperatureHumidityViewState()
    data class SocketErrorState(val message: String) : TemperatureHumidityViewState()
}