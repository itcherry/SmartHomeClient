package com.chernysh.smarthome.domain.interactor.bedroom.usecase

import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.model.TemperatureHumidityViewState
import com.chernysh.smarthome.domain.repository.TemperatureHumidityRepository
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Use case to getting temperature and humidity withing bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BedroomTemperatureHumidityUseCase @Inject constructor(
    private val temperatureHumidityRepository: TemperatureHumidityRepository) {

    fun getTemperatureHumidity(): Observable<TemperatureHumidityViewState> =
        Observable.merge(listOf(
            temperatureHumidityRepository.temperatureHumidityBedroomObservable(DataPolicy.SOCKET)
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