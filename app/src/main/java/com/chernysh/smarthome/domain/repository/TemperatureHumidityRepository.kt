package com.chernysh.smarthome.domain.repository

import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.model.TemperatureHumidityData
import io.reactivex.Observable

interface TemperatureHumidityRepository: SocketRepository {
    fun temperatureHumidityBedroomObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData>
    fun temperatureHumidityKitchenObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData>
    fun temperatureHumidityLivingRoomObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData>
    fun temperatureHumidityOutdoorObservable(dataPolicy: DataPolicy): Observable<TemperatureHumidityData>
}