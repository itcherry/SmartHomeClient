package ua.andrii.chernysh.rxsockets.data.repository

import io.reactivex.Observable
import ua.andrii.chernysh.kotlinrxsocket.socket.RxSocketEvent
import ua.andrii.chernysh.rxsockets.data.source.SocketDataSource
import ua.andrii.chernysh.rxsockets.domain.repository.SocketRepository

abstract class SocketRepositoryImpl(val socketDataSource: SocketDataSource): SocketRepository {
    override fun onSocketConnect() = socketDataSource.observableOnConnect()
    override fun onSocketDisconnect() = socketDataSource.observableOnDisconnect()
    override fun onSocketReconnect() = socketDataSource.observableOnReconnect()
    override fun onSocketReconnecting() = socketDataSource.observableOnReconnecting()
    override fun onSocketReconnectError() = socketDataSource.observableOnReconnectError()
    override fun onSocketError() = socketDataSource.observableOnError()
    override fun onSocketGenericEvent(): Observable<RxSocketEvent> = socketDataSource.observableOnGenericEvent()

    override fun connect() = socketDataSource.connect()
    override fun disconnect() = socketDataSource.disconnect()
}