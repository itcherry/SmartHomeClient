package com.chernysh.smarthome.domain.interactor.boiler.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BoilerPartialViewState
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.FlatPartialViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.repository.BoilerRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case to manipulate boiler state
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BoilerEnableUseCase @Inject constructor(private val boilerRepository: BoilerRepository) {
    fun processBoilerState(params: Data): Observable<BoilerPartialViewState.BoilerEnabledState> =
        when (params.method) {
            Method.GET -> boilerRepository.getState()
            Method.SET -> boilerRepository.setState(params.value).switchIfEmpty(Single.just(params.value))
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
            .map { BoilerPartialViewState.BoilerEnabledState(it) }


    data class Data(val method: Method, val value: Boolean = false)
}