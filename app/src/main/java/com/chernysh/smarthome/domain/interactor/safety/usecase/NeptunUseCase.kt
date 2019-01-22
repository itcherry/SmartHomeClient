package com.chernysh.smarthome.domain.interactor.safety.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.repository.BoilerRepository
import com.chernysh.smarthome.domain.repository.NeptunRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use case to get neptun system state
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class NeptunUseCase @Inject constructor(private val neptunRepository: NeptunRepository) {
    fun getNeptunState(): Observable<BooleanViewState> =
        neptunRepository.getState()
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