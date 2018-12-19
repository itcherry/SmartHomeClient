package com.chernysh.smarthome.data.network.source

/**
 * Copyright 2017. Andrii Chernysh
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.chernysh.smarthome.data.source.SocketDataSource
import io.reactivex.Observable
import ua.andrii.chernysh.kotlinrxsocket.socket.RxSocket
import ua.andrii.chernysh.kotlinrxsocket.socket.RxSocketEvent

/**
 * Manager to get entity socket data source.
 * Contains common string constants for socket purposes.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
abstract class SocketDataSource(private val rxSocket: RxSocket) : SocketDataSource {
    override fun observableOnConnect() = rxSocket.observableOnConnect()
    override fun observableOnDisconnect() = rxSocket.observableOnDisconnect()
    override fun observableOnReconnect() = rxSocket.observableOnReconnect()
    override fun observableOnReconnecting() = rxSocket.observableOnReconnecting()
    override fun observableOnReconnectError() = rxSocket.observableOnReconnectError()
    override fun observableOnError() = rxSocket.observableOnError()
    override fun observableOnGenericEvent(): Observable<RxSocketEvent> = rxSocket.observableOnGenericEvent()

    override fun connect() = rxSocket.connect()
    override fun disconnect() = rxSocket.close()
}
