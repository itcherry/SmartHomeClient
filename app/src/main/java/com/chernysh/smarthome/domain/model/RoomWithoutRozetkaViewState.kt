package com.chernysh.smarthome.domain.model

/**
 * Class that describes view state of some room, that contains lights and temperature
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
data class RoomWithoutRozetkaViewState(
    val lightsViewState: BooleanViewState,
    val temperatureHumidityViewState: TemperatureHumidityViewState
)