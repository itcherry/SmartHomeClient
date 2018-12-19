package com.chernysh.smarthome.data.source

import com.chernysh.smarthome.data.model.TemperatureHumidityDTO
import io.reactivex.Observable

/**
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
interface TemperatureHumidityDataSource: SocketDataSource {
    fun getTemperature(): Observable<TemperatureHumidityDTO>
}
