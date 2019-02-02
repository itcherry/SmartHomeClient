package com.chernysh.smarthome.domain.interactor.kitchen

import com.chernysh.smarthome.domain.interactor.kitchen.usecase.KitchenLightsUseCase
import com.chernysh.smarthome.domain.interactor.kitchen.usecase.KitchenTemperatureHumidityUseCase
import com.chernysh.smarthome.domain.model.Method
import javax.inject.Inject

/**
 * Interactor that manipulates data within bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class KitchenInteractor @Inject constructor(private val kitchenLightsUseCase: KitchenLightsUseCase,
                                            private val kitchenTemperatureHumidityUseCase: KitchenTemperatureHumidityUseCase) {
    fun onTemperatureHumidityObservable() = kitchenTemperatureHumidityUseCase.getTemperatureHumidity()

    fun getLightsStateObservable() = kitchenLightsUseCase.processKitchenLights(KitchenLightsUseCase.Data(Method.GET))

    fun enableLightsObservable() =
        kitchenLightsUseCase.processKitchenLights(KitchenLightsUseCase.Data(Method.SET, true))

    fun disableLightsObservable() =
        kitchenLightsUseCase.processKitchenLights(KitchenLightsUseCase.Data(Method.SET, false))
}