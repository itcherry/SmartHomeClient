package com.chernysh.smarthome.data.source

import com.chernysh.smarthome.data.model.TemperatureDTO
import io.reactivex.Observable

/**
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
interface TemperatureDataSource: SocketDataSource {
    fun getTemperatureBedroom(): Observable<TemperatureDTO>
    fun getTemperatureKitchen(): Observable<TemperatureDTO>
    fun getTemperatureLivingRoom(): Observable<TemperatureDTO>
    fun getTemperatureOutdoor(): Observable<TemperatureDTO>

}
