package com.chernysh.smarthome.data.repository
import com.chernysh.smarthome.data.source.SocketDataSource
import com.chernysh.smarthome.domain.repository.SocketRepository
import io.reactivex.Observable
import ua.andrii.chernysh.kotlinrxsocket.socket.RxSocketEvent


abstract class SocketRepositoryImpl(private val socketDataSource: SocketDataSource): SocketRepository {
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