package com.chernysh.smarthome.data.source

import io.reactivex.Observable
import ua.andrii.chernysh.kotlinrxsocket.socket.RxSocketEvent

interface SocketDataSource {
    fun observableOnConnect(): Observable<Unit>
    fun observableOnDisconnect(): Observable<Unit>
    fun observableOnReconnect() : Observable<Unit>
    fun observableOnReconnecting() : Observable<Unit>
    fun observableOnReconnectError() : Observable<String>
    fun observableOnError(): Observable<String>
    fun observableOnGenericEvent(): Observable<RxSocketEvent>

    fun connect()
    fun disconnect()
}