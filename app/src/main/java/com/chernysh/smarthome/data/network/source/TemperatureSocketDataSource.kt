package com.chernysh.smarthome.data.network.source

import com.chernysh.smarthome.data.model.TemperatureDTO
import com.chernysh.smarthome.data.source.TemperatureDataSource
import io.reactivex.Observable
import ua.andrii.chernysh.kotlinrxsocket.socket.RxSocket
import javax.inject.Inject


/**
 * Copyright 2017. Andrii Chernysh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
class TemperatureSocketDataSource @Inject constructor(val socket: RxSocket) :
        SocketDataSource(socket), TemperatureDataSource {
    override fun getTemperatureKitchen(): Observable<TemperatureDTO> =
        socket.observableOn(TEMP_HUM_KITCHEN_EVENT, TemperatureDTO::class.java)

    override fun getTemperatureLivingRoom(): Observable<TemperatureDTO> =
        socket.observableOn(TEMP_HUM_LIVING_ROOM_EVENT, TemperatureDTO::class.java)

    override fun getTemperatureOutdoor(): Observable<TemperatureDTO> =
        socket.observableOn(TEMP_HUM_OUTDOOR_EVENT, TemperatureDTO::class.java)

    override fun getTemperatureBedroom(): Observable<TemperatureDTO> =
        socket.observableOn(TEMP_HUM_BEDROOM_EVENT, TemperatureDTO::class.java)

    companion object {
        const val TEMP_HUM_BEDROOM_EVENT = "tempHumBedroom"
        const val TEMP_HUM_KITCHEN_EVENT = "tempHumKitchen"
        const val TEMP_HUM_LIVING_ROOM_EVENT = "tempHumLivingRoom"
        const val TEMP_HUM_OUTDOOR_EVENT = "tempHumOutdoor"
    }
}