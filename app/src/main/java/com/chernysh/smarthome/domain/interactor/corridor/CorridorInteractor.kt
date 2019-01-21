package com.chernysh.smarthome.domain.interactor.corridor

import com.chernysh.smarthome.domain.model.Method
import javax.inject.Inject

/**
 * Interactor that manipulates data within corridor
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class CorridorInteractor @Inject constructor(private val corridorLightsUseCase: KitchenLightsUseCase) {
    fun getLightsStateObservable() = corridorLightsUseCase.processCorridorLights(KitchenLightsUseCase.Data(Method.GET))

    fun enableLightsObservable() =
        corridorLightsUseCase.processCorridorLights(KitchenLightsUseCase.Data(Method.SET, true))

    fun disableLightsObservable() =
        corridorLightsUseCase.processCorridorLights(KitchenLightsUseCase.Data(Method.SET, false))
}