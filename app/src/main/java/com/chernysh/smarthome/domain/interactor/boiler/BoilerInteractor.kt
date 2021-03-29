package com.chernysh.smarthome.domain.interactor.boiler

import com.chernysh.smarthome.domain.interactor.boiler.usecase.BoilerEnableUseCase
import com.chernysh.smarthome.domain.interactor.boiler.usecase.BoilerScheduleEnableUseCase
import com.chernysh.smarthome.domain.interactor.boiler.usecase.BoilerScheduleUseCase
import com.chernysh.smarthome.domain.model.BoilerPartialViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.timerangepicker.TimeRange
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * Interactor that manipulates data within corridor
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
@Suppress("UNCHECKED_CAST")
class BoilerInteractor @Inject constructor(
    private val boilerEnableUseCase: BoilerEnableUseCase,
    private val boilerScheduleUseCase: BoilerScheduleUseCase,
    private val boilerScheduleStateUseCase: BoilerScheduleEnableUseCase,
    private val schedulersTransformer: ObservableTransformer<Any, Any>
) {
    // Boiler enabled overall
    fun getBoilerEnabledStateObservable() =
        boilerEnableUseCase.processBoilerState(BoilerEnableUseCase.Data(Method.GET))
            .compose(schedulersTransformer as ObservableTransformer<BoilerPartialViewState, BoilerPartialViewState>)

    fun enableBoilerObservable() =
        boilerEnableUseCase.processBoilerState(BoilerEnableUseCase.Data(Method.SET, true))
            .compose(schedulersTransformer as ObservableTransformer<BoilerPartialViewState, BoilerPartialViewState>)

    fun disableBoilerObservable() =
        boilerEnableUseCase.processBoilerState(BoilerEnableUseCase.Data(Method.SET, false))
            .compose(schedulersTransformer as ObservableTransformer<BoilerPartialViewState, BoilerPartialViewState>)

    // Boiler schedule enabled
    fun getBoilerScheduleEnabledStateObservable() =
        boilerScheduleStateUseCase.processBoilerEnabledState(BoilerScheduleEnableUseCase.Data(Method.GET))
            .compose(schedulersTransformer as ObservableTransformer<BoilerPartialViewState, BoilerPartialViewState>)

    fun enableBoilerScheduleObservable() =
        boilerScheduleStateUseCase.processBoilerEnabledState(BoilerScheduleEnableUseCase.Data(Method.SET, true))
            .compose(schedulersTransformer as ObservableTransformer<BoilerPartialViewState, BoilerPartialViewState>)

    fun disableBoilerScheduleObservable() =
        boilerScheduleStateUseCase.processBoilerEnabledState(BoilerScheduleEnableUseCase.Data(Method.SET, false))
            .compose(schedulersTransformer as ObservableTransformer<BoilerPartialViewState, BoilerPartialViewState>)

    // Boiler schedule
    fun getBoilerScheduleObservable() =
        boilerScheduleUseCase.processBoilerSchedule(BoilerScheduleUseCase.Data(Method.GET))
            .compose(schedulersTransformer as ObservableTransformer<BoilerPartialViewState, BoilerPartialViewState>)

    fun saveBoilerSchedule(timeRanges: List<TimeRange>) =
        boilerScheduleUseCase.processBoilerSchedule(BoilerScheduleUseCase.Data(Method.SET, timeRanges))
            .compose(schedulersTransformer as ObservableTransformer<BoilerPartialViewState, BoilerPartialViewState>)

}