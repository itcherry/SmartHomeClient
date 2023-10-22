package com.chernysh.smarthome.domain.interactor.safety

import com.chernysh.smarthome.domain.interactor.safety.usecase.*
import com.chernysh.smarthome.domain.model.FlatPartialViewState
import com.chernysh.smarthome.domain.model.Method
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * Copyright 2020. Andrii Chernysh
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Interactor that manipulates data within overall flat
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
@Suppress("UNCHECKED_CAST")
class SafetyInteractor @Inject constructor(
    private val temperatureHumidityOutsideUseCase: TemperatureHumidityOutsideUseCase,
    private val alarmUseCase: AlarmUseCase,
    private val securityUseCase: SecurityUseCase,
    private val neptunUseCase: NeptunUseCase,
    private val schedulersTransformer: ObservableTransformer<Any, Any>
) {

    /* Temperature outside */
    fun onTemperatureHumidityObservable() = temperatureHumidityOutsideUseCase.getTemperatureHumidityOutside()
        .compose(schedulersTransformer as ObservableTransformer<FlatPartialViewState, FlatPartialViewState>)

    /* Neptun */
    fun getNeptunStateObservable() = neptunUseCase.getNeptunState()
        .compose(schedulersTransformer as ObservableTransformer<FlatPartialViewState.NeptunState, FlatPartialViewState.NeptunState>)

    /* Alarm */
    fun getAlarmStateObservable() = alarmUseCase.processAlarmState(AlarmUseCase.Data(Method.GET))
        .compose(schedulersTransformer as ObservableTransformer<FlatPartialViewState.AlarmState, FlatPartialViewState.AlarmState>)

    fun enableAlarmObservable() =
        alarmUseCase.processAlarmState(AlarmUseCase.Data(Method.SET, true))
            .compose(schedulersTransformer as ObservableTransformer<FlatPartialViewState.AlarmState, FlatPartialViewState.AlarmState>)

    fun disableAlarmObservable() =
        alarmUseCase.processAlarmState(AlarmUseCase.Data(Method.SET, false))
            .compose(schedulersTransformer as ObservableTransformer<FlatPartialViewState.AlarmState, FlatPartialViewState.AlarmState>)

    /* Security */
    fun getSecurityStateObservable() =
        securityUseCase.processSecurityState(SecurityUseCase.Data(Method.GET))
            .compose(schedulersTransformer as ObservableTransformer<FlatPartialViewState.SecurityState, FlatPartialViewState.SecurityState>)

    fun enableSecurityObservable() =
        securityUseCase.processSecurityState(SecurityUseCase.Data(Method.SET, true))
            .compose(schedulersTransformer as ObservableTransformer<FlatPartialViewState.SecurityState, FlatPartialViewState.SecurityState>)

    fun disableSecurityObservable() =
        securityUseCase.processSecurityState(SecurityUseCase.Data(Method.SET, false))
            .compose(schedulersTransformer as ObservableTransformer<FlatPartialViewState.SecurityState, FlatPartialViewState.SecurityState>)

    fun getFireStateObservable() =
        securityUseCase.processFireState(SecurityUseCase.Data(Method.GET))
            .compose(schedulersTransformer as ObservableTransformer<FlatPartialViewState.FireState, FlatPartialViewState.FireState>)

}