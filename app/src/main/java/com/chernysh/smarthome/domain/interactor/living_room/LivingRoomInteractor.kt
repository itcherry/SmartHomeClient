package com.chernysh.smarthome.domain.interactor.living_room

import com.chernysh.smarthome.domain.interactor.living_room.usecase.LivingRoomRozetkaUseCase
import com.chernysh.smarthome.domain.interactor.living_room.usecase.LivingRoomTemperatureHumidityUseCase
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.model.RoomPartialWithoutLightsViewState
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * Interactor that manipulates data within living room
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
@Suppress("UNCHECKED_CAST")
class LivingRoomInteractor @Inject constructor(
    private val livingRoomTemperatureHumidityUseCase: LivingRoomTemperatureHumidityUseCase,
    private val livingRoomRozetkaUseCase: LivingRoomRozetkaUseCase,
    private val schedulersTransformer: ObservableTransformer<Any, Any>) {

    fun onTemperatureHumidityObservable() = livingRoomTemperatureHumidityUseCase.getTemperatureHumidity()
            .compose(schedulersTransformer as ObservableTransformer<RoomPartialWithoutLightsViewState, RoomPartialWithoutLightsViewState>)

    fun getRozetkaStateObservable() =
        livingRoomRozetkaUseCase.processLivingRoomRozetka(LivingRoomRozetkaUseCase.Data(Method.GET))
                .compose(schedulersTransformer as ObservableTransformer<RoomPartialWithoutLightsViewState, RoomPartialWithoutLightsViewState>)

    fun enableRozetkaObservable() =
        livingRoomRozetkaUseCase.processLivingRoomRozetka(LivingRoomRozetkaUseCase.Data(Method.SET, true))
                .compose(schedulersTransformer as ObservableTransformer<RoomPartialWithoutLightsViewState, RoomPartialWithoutLightsViewState>)

    fun disableRozetkaObservable() =
        livingRoomRozetkaUseCase.processLivingRoomRozetka(LivingRoomRozetkaUseCase.Data(Method.SET, false))
                .compose(schedulersTransformer as ObservableTransformer<RoomPartialWithoutLightsViewState, RoomPartialWithoutLightsViewState>)
}