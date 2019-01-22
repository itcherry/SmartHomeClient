package com.chernysh.smarthome.domain.interactor.safety.usecase

import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.model.TemperatureHumidityViewState
import com.chernysh.smarthome.domain.repository.TemperatureHumidityRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use case to getting temperature and humidity outside of flat
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class TemperatureHumidityOutsideUseCase @Inject constructor(
    private val temperatureHumidityRepository: TemperatureHumidityRepository) {

    fun getTemperatureHumidityOutside(): Observable<TemperatureHumidityViewState> =
        Observable.merge(listOf(
            temperatureHumidityRepository.temperatureHumidityOutdoorObservable(DataPolicy.SOCKET)
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
}