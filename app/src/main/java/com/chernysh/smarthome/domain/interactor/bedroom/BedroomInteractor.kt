package com.chernysh.smarthome.domain.interactor.bedroom

import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.interactor.bedroom.usecase.BedroomLightsUseCase
import com.chernysh.smarthome.domain.interactor.bedroom.usecase.BedroomRozetkaUseCase
import com.chernysh.smarthome.domain.interactor.bedroom.usecase.BedroomTemperatureHumidityUseCase
import com.chernysh.smarthome.domain.model.TemperatureHumidityData
import com.chernysh.smarthome.domain.repository.TemperatureHumidityRepository
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
    private val bedroomTemperatureHumidityUseCase: BedroomTemperatureHumidityUseCase,
    private val bedroomLightsUseCase: BedroomLightsUseCase,
    private val bedroomRozetkaUseCase: BedroomRozetkaUseCase,
    private val temperatureHumidityRepository: TemperatureHumidityRepository) {

    fun onTemperatureHumidityObservable() = bedroomTemperatureHumidityUseCase.getTemperatureHumidity()

    fun getLightsStateObservable() = bedroomLightsUseCase.processBedroomLights(BedroomLightsUseCase.Data(Method.GET))

    fun enableLightsObservable() =
        bedroomLightsUseCase.processBedroomLights(BedroomLightsUseCase.Data(Method.SET, true))

    fun disableLightsObservable() =
        bedroomLightsUseCase.processBedroomLights(BedroomLightsUseCase.Data(Method.SET, false))

    fun getRozetkaStateObservable() =
        bedroomRozetkaUseCase.processBedroomRozetka(BedroomRozetkaUseCase.Data(Method.GET))

    fun enableRozetkaObservable() =
        bedroomRozetkaUseCase.processBedroomRozetka(BedroomRozetkaUseCase.Data(Method.SET, true))

    fun disableRozetkaObservable() =
        bedroomRozetkaUseCase.processBedroomRozetka(BedroomRozetkaUseCase.Data(Method.SET, false))
}