package com.chernysh.smarthome.domain.interactor.bedroom.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.model.RoomPartialViewState
import com.chernysh.smarthome.domain.repository.BedroomRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use case to manipulate lights state withing bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BedroomLightsUseCase @Inject constructor(private val bedroomRepository: BedroomRepository) {
    fun processBedroomLights(params: Data): Observable<RoomPartialViewState> =
        when (params.method) {
            Method.GET -> bedroomRepository.getLightState()
            Method.SET -> bedroomRepository.setLightState(params.value).toSingle().map { params.value }
        }
            .toObservable()
            .map<BooleanViewState> { BooleanViewState.DataState(it) }
            .startWith { BooleanViewState.LoadingState }
            .onErrorReturn {
                if (it is NoConnectivityException) {
                    BooleanViewState.ConnectivityErrorState
                } else {
                    BooleanViewState.ErrorState(it)
                }
            }
            .map { RoomPartialViewState.LightsState(it) }


    data class Data(val method: Method, val value: Boolean = false)
}