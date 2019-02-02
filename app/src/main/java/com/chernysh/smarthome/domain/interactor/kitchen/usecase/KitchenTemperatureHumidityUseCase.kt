package com.chernysh.smarthome.domain.interactor.kitchen.usecase

import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.model.RoomPartialWithoutRozetkaViewState
import com.chernysh.smarthome.domain.model.TemperatureHumidityViewState
import com.chernysh.smarthome.domain.repository.TemperatureHumidityRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use case to getting temperature and humidity withing kitchen
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class KitchenTemperatureHumidityUseCase @Inject constructor(
    private val temperatureHumidityRepository: TemperatureHumidityRepository) {

    fun getTemperatureHumidity(): Observable<RoomPartialWithoutRozetkaViewState> =
        Observable.merge(listOf(
            temperatureHumidityRepository.temperatureHumidityKitchenObservable(DataPolicy.SOCKET)
                .map { TemperatureHumidityViewState.DataState(it) },
            temperatureHumidityRepository.onSocketConnect().map { TemperatureHumidityViewState.SocketConnectedState },
            temperatureHumidityRepository.onSocketDisconnect().map { TemperatureHumidityViewState.SocketDisconnectedState },
            temperatureHumidityRepository.onSocketReconnecting().map { TemperatureHumidityViewState.SocketReconnectingState },
            temperatureHumidityRepository.onSocketError().map { TemperatureHumidityViewState.SocketErrorState(it) }
        ))
            .doOnSubscribe { temperatureHumidityRepository.connect() }
            .doOnDispose { temperatureHumidityRepository.disconnect() }
            .startWith { TemperatureHumidityViewState.NoDataState }
            .onErrorReturn { TemperatureHumidityViewState.ErrorState(it) }
            .map { RoomPartialWithoutRozetkaViewState.TemperatureHumidityState(it) }

}