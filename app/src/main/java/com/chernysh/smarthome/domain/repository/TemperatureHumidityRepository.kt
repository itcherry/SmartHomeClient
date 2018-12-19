package com.chernysh.smarthome.domain.repository

import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.model.TemperatureHumidityData
import io.reactivex.Observable

interface TemperatureHumidityRepository: SocketRepository {
    fun temperatureHumidityObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData>
}