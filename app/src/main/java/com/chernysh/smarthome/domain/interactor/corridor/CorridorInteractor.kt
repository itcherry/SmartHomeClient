package com.chernysh.smarthome.domain.interactor.corridor

import com.chernysh.smarthome.domain.interactor.bedroom.usecase.BedroomLightsUseCase
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.repository.CorridorRepository
import javax.inject.Inject

/**
 * Interactor that manipulates data within bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class CorridorInteractor @Inject constructor(private val corridorLightsUseCase: CorridorLightsUseCase) {
    fun getLightsStateObservable() = corridorLightsUseCase.processCorridorLights(CorridorLightsUseCase.Data(Method.GET))

    fun enableLightsObservable() =
        corridorLightsUseCase.processCorridorLights(CorridorLightsUseCase.Data(Method.SET, true))

    fun disableLightsObservable() =
        corridorLightsUseCase.processCorridorLights(CorridorLightsUseCase.Data(Method.SET, false))
}