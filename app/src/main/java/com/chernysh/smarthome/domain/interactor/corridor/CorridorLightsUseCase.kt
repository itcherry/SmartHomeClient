package com.chernysh.smarthome.domain.interactor.corridor

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.repository.BedroomRepository
import com.chernysh.smarthome.domain.repository.CorridorRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use case to manipulate lights state withing corridor
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class CorridorLightsUseCase @Inject constructor(private val corridorRepository: CorridorRepository) {
    fun processCorridorLights(params: Data): Observable<BooleanViewState> =
        when (params.method) {
            Method.GET -> corridorRepository.getLightState()
            Method.SET -> corridorRepository.setLightState(params.value).toSingle().map { params.value }
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