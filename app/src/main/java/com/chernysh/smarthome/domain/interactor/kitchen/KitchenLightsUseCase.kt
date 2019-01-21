package com.chernysh.smarthome.domain.interactor.kitchen

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.repository.BedroomRepository
import com.chernysh.smarthome.domain.repository.CorridorRepository
import com.chernysh.smarthome.domain.repository.KitchenRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use case to manipulate lights state withing kitchen
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class KitchenLightsUseCase @Inject constructor(private val kitchenRepository: KitchenRepository) {
    fun processKitchenLights(params: Data): Observable<BooleanViewState> =
        when (params.method) {
            Method.GET -> kitchenRepository.getLightState()
            Method.SET -> kitchenRepository.setLightState(params.value).toSingle().map { params.value }
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


    data class Data(val method: Method, val value: Boolean = false)
}