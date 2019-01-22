package com.chernysh.smarthome.domain.interactor.safety

import com.chernysh.smarthome.domain.interactor.bedroom.usecase.BedroomLightsUseCase
import com.chernysh.smarthome.domain.interactor.bedroom.usecase.BedroomRozetkaUseCase
import com.chernysh.smarthome.domain.interactor.safety.usecase.*
import com.chernysh.smarthome.domain.model.Method
import javax.inject.Inject

/**
 * Copyright 2018. Andrii Chernysh
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
 * Interactor that manipulates data within bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BedroomInteractor @Inject constructor(
    private val temperatureHumidityOutsideUseCase: TemperatureHumidityOutsideUseCase,
    private val alarmUseCase: AlarmUseCase,
    private val boilerUseCase: BoilerUseCase,
    private val doorUseCase: DoorUseCase,
    private val neptunUseCase: NeptunUseCase) {

    /* Temperature outside */
    fun onTemperatureHumidityObservable() = temperatureHumidityOutsideUseCase.getTemperatureHumidityOutside()

    /* Neptun */
    fun getNeptunStateObservable() = neptunUseCase.getNeptunState()

    /* Alarm */
    fun getAlarmStateObservable() = alarmUseCase.processAlarmState(AlarmUseCase.Data(Method.GET))

    fun enableAlarmObservable() =
        alarmUseCase.processAlarmState(AlarmUseCase.Data(Method.SET, true))

    fun disableAlarmObservable() =
        alarmUseCase.processAlarmState(AlarmUseCase.Data(Method.SET, false))

    /* Boiler */
    fun getBoilerStateObservable() =
        boilerUseCase.processBoilerState(BoilerUseCase.Data(Method.GET))

    fun enableBoilerObservable() =
        boilerUseCase.processBoilerState(BoilerUseCase.Data(Method.SET, true))

    fun disableBoilerObservable() =
        boilerUseCase.processBoilerState(BoilerUseCase.Data(Method.SET, false))

    /* Door */
    fun getDoorStateObservable() =
        doorUseCase.processDoorState(DoorUseCase.Data(Method.GET))

    fun enableDoorObservable() =
        doorUseCase.processDoorState(DoorUseCase.Data(Method.SET, true))

    fun disableDoorObservable() =
        doorUseCase.processDoorState(DoorUseCase.Data(Method.SET, false))

}