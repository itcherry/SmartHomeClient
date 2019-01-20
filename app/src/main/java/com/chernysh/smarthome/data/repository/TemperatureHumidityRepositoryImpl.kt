package com.chernysh.smarthome.data.repository

import com.chernysh.smarthome.data.exception.DataPolicyNotImplemented
import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.data.source.TemperatureHumidityDataSource
import com.chernysh.smarthome.domain.model.TemperatureHumidityData
import com.chernysh.smarthome.domain.repository.TemperatureHumidityRepository
import io.reactivex.Observable
import javax.inject.Inject

class TemperatureHumidityRepositoryImpl @Inject constructor(
    private val temperatureHumidityDataSource: TemperatureHumidityDataSource
) : SocketRepositoryImpl(temperatureHumidityDataSource), TemperatureHumidityRepository {
    override fun temperatureHumidityBedroomObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData> =
        when (dataPolicy) {
            DataPolicy.SOCKET -> temperatureHumidityDataSource.getTemperatureBedroom().map { it.mapToEntity() }
            else -> Observable.error(DataPolicyNotImplemented(dataPolicy))
        }

    override fun temperatureHumidityKitchenObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData> =
        when (dataPolicy) {
            DataPolicy.SOCKET -> temperatureHumidityDataSource.getTemperatureKitchen().map { it.mapToEntity() }
            else -> Observable.error(DataPolicyNotImplemented(dataPolicy))
        }

    override fun temperatureHumidityLivingRoomObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData> =
        when (dataPolicy) {
            DataPolicy.SOCKET -> temperatureHumidityDataSource.getTemperatureLivingRoom().map { it.mapToEntity() }
            else -> Observable.error(DataPolicyNotImplemented(dataPolicy))
        }

    override fun temperatureHumidityOutdoorObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData> =
        when (dataPolicy) {
            DataPolicy.SOCKET -> temperatureHumidityDataSource.getTemperatureOutdoor().map { it.mapToEntity() }
            else -> Observable.error(DataPolicyNotImplemented(dataPolicy))
        }
}