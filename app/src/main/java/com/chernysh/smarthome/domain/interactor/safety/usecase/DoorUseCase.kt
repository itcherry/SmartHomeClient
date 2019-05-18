package com.chernysh.smarthome.domain.interactor.safety.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.FlatPartialViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.repository.BoilerRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use case to manipulate door state
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class DoorUseCase @Inject constructor(private val doorRepository: BoilerRepository) {
    fun processDoorState(params: Data): Observable<FlatPartialViewState.DoorState> =
        when (params.method) {
            Method.GET -> doorRepository.getState()
            Method.SET -> doorRepository.setState(params.value).toSingle().map { params.value }
        }
            .toObservable()
            .map<BooleanViewState> { BooleanViewState.DataState(it) }
            .startWith(BooleanViewState.LoadingState)
            .onErrorReturn {
                if (it is NoConnectivityException) {
                    BooleanViewState.ConnectivityErrorState
                } else {
                    BooleanViewState.ErrorState(it)
                }
            }
            .map { FlatPartialViewState.DoorState(it) }


    data class Data(val method: Method, val value: Boolean = false)
}