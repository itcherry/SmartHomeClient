package com.chernysh.smarthome.domain.interactor.safety.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.FlatPartialViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.repository.SecurityRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case to manipulate security state
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class SecurityUseCase @Inject constructor(private val securityRepository: SecurityRepository) {
    fun processSecurityState(params: Data): Observable<FlatPartialViewState.SecurityState> =
        when (params.method) {
            Method.GET -> securityRepository.getState()
            Method.SET -> securityRepository.setState(params.value).switchIfEmpty(Single.just(params.value))
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
            .map { FlatPartialViewState.SecurityState(it) }

    fun processFireState(params: Data): Observable<FlatPartialViewState.FireState> =
        securityRepository.isFireAtHome()
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
            .map { FlatPartialViewState.FireState(it) }


    data class Data(val method: Method, val value: Boolean = false)
}