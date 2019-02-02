package com.chernysh.smarthome.domain.interactor.living_room

import com.chernysh.smarthome.domain.interactor.living_room.usecase.LivingRoomRozetkaUseCase
import com.chernysh.smarthome.domain.interactor.living_room.usecase.LivingRoomTemperatureHumidityUseCase
import com.chernysh.smarthome.domain.model.Method
import javax.inject.Inject

/**
 * Interactor that manipulates data within living room
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class LivingRoomInteractor @Inject constructor(
    private val livingRoomTemperatureHumidityUseCase: LivingRoomTemperatureHumidityUseCase,
    private val livingRoomRozetkaUseCase: LivingRoomRozetkaUseCase) {

    fun onTemperatureHumidityObservable() = livingRoomTemperatureHumidityUseCase.getTemperatureHumidity()

    fun getRozetkaStateObservable() =
        livingRoomRozetkaUseCase.processLivingRoomRozetka(LivingRoomRozetkaUseCase.Data(Method.GET))

    fun enableRozetkaObservable() =
        livingRoomRozetkaUseCase.processLivingRoomRozetka(LivingRoomRozetkaUseCase.Data(Method.SET, true))

    fun disableRozetkaObservable() =
        livingRoomRozetkaUseCase.processLivingRoomRozetka(LivingRoomRozetkaUseCase.Data(Method.SET, false))
}