package com.chernysh.smarthome.domain.interactor.safety.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.FlatPartialViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.model.RoomPartialViewState
import com.chernysh.smarthome.domain.repository.AlarmRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case to manipulate alarm state
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class AlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {
    fun processAlarmState(params: Data): Observable<FlatPartialViewState.AlarmState> =
        when (params.method) {
            Method.GET -> alarmRepository.getState()
            Method.SET -> alarmRepository.setState(params.value).switchIfEmpty(Single.just(params.value))
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
            .map { FlatPartialViewState.AlarmState(it) }


    data class Data(val method: Method, val value: Boolean = false)
}