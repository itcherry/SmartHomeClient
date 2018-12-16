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

    override fun temperatureHumidityObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData> {
        return when (dataPolicy) {
            DataPolicy.SOCKET -> temperatureHumidityDataSource.getTemperature().map { it.mapToEntity() }
            else -> Observable.error(DataPolicyNotImplemented(dataPolicy))
        }
    }
}