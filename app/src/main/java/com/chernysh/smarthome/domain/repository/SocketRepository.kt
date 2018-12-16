package com.chernysh.smarthome.domain.repository
import io.reactivex.Observable
import ua.andrii.chernysh.kotlinrxsocket.socket.RxSocketEvent

interface SocketRepository{
    fun onSocketConnect(): Observable<Unit>
    fun onSocketDisconnect(): Observable<Unit>
    fun onSocketReconnect() : Observable<Unit>
    fun onSocketReconnecting() : Observable<Unit>
    fun onSocketReconnectError() : Observable<String>
    fun onSocketError(): Observable<String>
    fun onSocketGenericEvent(): Observable<RxSocketEvent>

    fun connect()
    fun disconnect()
}