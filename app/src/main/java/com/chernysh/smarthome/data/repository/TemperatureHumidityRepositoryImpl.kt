package ua.andrii.chernysh.rxsockets.data.repository

import io.reactivex.Observable
import ua.andrii.chernysh.rxsockets.data.exception.DataPolicyNotImplemented
import ua.andrii.chernysh.rxsockets.data.source.DataPolicy
import ua.andrii.chernysh.rxsockets.data.source.TemperatureHumidityDataSource
import ua.andrii.chernysh.rxsockets.domain.model.TemperatureHumidityData
import ua.andrii.chernysh.rxsockets.domain.repository.TemperatureHumidityRepository
import javax.inject.Inject

class TemperatureHumidityRepositoryImpl @Inject constructor(
        private val temperatureHumidityDataSource: TemperatureHumidityDataSource
) : SocketRepositoryImpl(temperatureHumidityDataSource), TemperatureHumidityRepository {

    override fun temperatureHumidityObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData> {
        return when(dataPolicy){
            DataPolicy.SOCKET -> temperatureHumidityDataSource.getTemperature().map { it.mapToEntity() }
            else -> Observable.error(DataPolicyNotImplemented(dataPolicy))
        }
    }
}