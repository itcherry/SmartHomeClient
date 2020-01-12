package com.chernysh.smarthome.domain.interactor.living_room.usecase

import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.model.LivingRoomPartialViewState
import com.chernysh.smarthome.domain.model.TemperatureHumidityViewState
import com.chernysh.smarthome.domain.repository.TemperatureHumidityRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use case to getting temperature and humidity withing bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class LivingRoomTemperatureHumidityUseCase @Inject constructor(
    private val temperatureHumidityRepository: TemperatureHumidityRepository) {

    fun getTemperatureHumidity(): Observable<LivingRoomPartialViewState> =
        Observable.merge(listOf(
            temperatureHumidityRepository.temperatureHumidityLivingRoomObservable(DataPolicy.SOCKET)
                .map { TemperatureHumidityViewState.DataState(it) },
            temperatureHumidityRepository.onSocketConnect().map { TemperatureHumidityViewState.SocketConnectedState },
            temperatureHumidityRepository.onSocketDisconnect().map { TemperatureHumidityViewState.SocketDisconnectedState },
            temperatureHumidityRepository.onSocketReconnecting().map { TemperatureHumidityViewState.SocketReconnectingState },
            temperatureHumidityRepository.onSocketError().map { TemperatureHumidityViewState.SocketErrorState(it) }
        ))
            .doOnSubscribe { temperatureHumidityRepository.connect() }
            .doOnDispose { temperatureHumidityRepository.disconnect() }
            .startWith(TemperatureHumidityViewState.NoDataState)
            .onErrorReturn { TemperatureHumidityViewState.ErrorState(it) }
            .map { LivingRoomPartialViewState.TemperatureHumidityState(it) }
}