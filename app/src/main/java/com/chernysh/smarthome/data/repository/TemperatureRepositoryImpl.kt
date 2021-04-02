package com.chernysh.smarthome.data.repository

import com.chernysh.smarthome.data.exception.DataPolicyNotImplemented
import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.data.source.TemperatureDataSource
import com.chernysh.smarthome.domain.model.TemperatureData
import com.chernysh.smarthome.domain.repository.TemperatureHumidityRepository
import io.reactivex.Observable
import javax.inject.Inject

class TemperatureRepositoryImpl @Inject constructor(
    private val temperatureDataSource: TemperatureDataSource
) : SocketRepositoryImpl(temperatureDataSource), TemperatureHumidityRepository {
    override fun temperatureHumidityBedroomObservable(dataPolicy: DataPolicy): Observable<TemperatureData> =
        when (dataPolicy) {
            DataPolicy.SOCKET -> temperatureDataSource.getTemperatureBedroom().map { it.mapToEntity() }
            else -> Observable.error(DataPolicyNotImplemented(dataPolicy))
        }

    override fun temperatureHumidityKitchenObservable(dataPolicy: DataPolicy): Observable<TemperatureData> =
        when (dataPolicy) {
            DataPolicy.SOCKET -> temperatureDataSource.getTemperatureKitchen().map { it.mapToEntity() }
            else -> Observable.error(DataPolicyNotImplemented(dataPolicy))
        }

    override fun temperatureHumidityLivingRoomObservable(dataPolicy: DataPolicy): Observable<TemperatureData> =
        when (dataPolicy) {
            DataPolicy.SOCKET -> temperatureDataSource.getTemperatureLivingRoom().map { it.mapToEntity() }
            else -> Observable.error(DataPolicyNotImplemented(dataPolicy))
        }

    override fun temperatureHumidityOutdoorObservable(dataPolicy: DataPolicy): Observable<TemperatureData> =
        when (dataPolicy) {
            DataPolicy.SOCKET -> temperatureDataSource.getTemperatureOutdoor().map { it.mapToEntity() }
            else -> Observable.error(DataPolicyNotImplemented(dataPolicy))
        }
}