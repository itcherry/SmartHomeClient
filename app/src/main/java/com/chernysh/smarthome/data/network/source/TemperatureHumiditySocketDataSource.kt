package ua.andrii.chernysh.rxsockets.data.network.source

import io.reactivex.Observable
import ua.andrii.chernysh.kotlinrxsocket.socket.RxSocket
import ua.andrii.chernysh.rxsockets.data.model.TemperatureHumidityDTO
import ua.andrii.chernysh.rxsockets.data.source.TemperatureHumidityDataSource
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
class TemperatureHumiditySocketDataSource @Inject constructor(val socket: RxSocket) :
        SocketDataSource(socket), TemperatureHumidityDataSource {

    override fun getTemperature(): Observable<TemperatureHumidityDTO> =
        socket.observableOn(TEMP_HUM_EVENT, TemperatureHumidityDTO::class.java)

    companion object {
        const val TEMP_HUM_EVENT = "tempHum"
    }
}