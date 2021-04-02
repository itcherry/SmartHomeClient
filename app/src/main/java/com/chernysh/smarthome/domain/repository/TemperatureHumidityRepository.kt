package com.chernysh.smarthome.domain.repository

import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.model.TemperatureData
import io.reactivex.Observable

interface TemperatureHumidityRepository: SocketRepository {
    fun temperatureHumidityBedroomObservable(dataPolicy: DataPolicy): Observable<TemperatureData>
    fun temperatureHumidityKitchenObservable(dataPolicy: DataPolicy): Observable<TemperatureData>
    fun temperatureHumidityLivingRoomObservable(dataPolicy: DataPolicy): Observable<TemperatureData>
    fun temperatureHumidityOutdoorObservable(dataPolicy: DataPolicy): Observable<TemperatureData>
}