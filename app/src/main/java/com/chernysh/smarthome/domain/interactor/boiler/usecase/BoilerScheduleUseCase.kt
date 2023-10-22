package com.chernysh.smarthome.domain.interactor.boiler.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BoilerPartialViewState
import com.chernysh.smarthome.domain.model.BoilerScheduleViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.repository.BoilerRepository
import com.chernysh.timerangepicker.TimeRange
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case to manipulate boiler schedule
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BoilerScheduleUseCase @Inject constructor(private val boilerRepository: BoilerRepository) {
    fun processBoilerSchedule(params: Data): Observable<BoilerPartialViewState.BoilerScheduleState> =
        when (params.method) {
            Method.GET -> boilerRepository.getSchedule()
                .map<BoilerScheduleViewState> { BoilerScheduleViewState.DataState(it) }
            Method.SET -> boilerRepository.setSchedule(params.value)
                .switchIfEmpty(
                    Single.just<BoilerScheduleViewState>(BoilerScheduleViewState.SubmitSuccessState(params.value))
                )
                .map { it as BoilerScheduleViewState }
        }.toObservable()
            .startWith(BoilerScheduleViewState.LoadingState)
            .onErrorReturn {
                if (it is NoConnectivityException) {
                    BoilerScheduleViewState.ConnectivityErrorState
                } else {
                    BoilerScheduleViewState.ErrorState(it)
                }
            }
            .map { BoilerPartialViewState.BoilerScheduleState(it) }


    data class Data(val method: Method, val value: List<TimeRange> = listOf())
}