package com.chernysh.smarthome.domain.interactor.living_room

import com.chernysh.smarthome.domain.interactor.bedroom.usecase.BedroomLightsUseCase
import com.chernysh.smarthome.domain.interactor.living_room.usecase.LivingRoomAquariumUseCase
import com.chernysh.smarthome.domain.interactor.living_room.usecase.LivingRoomLightsUseCase
import com.chernysh.smarthome.domain.interactor.living_room.usecase.LivingRoomRozetkaUseCase
import com.chernysh.smarthome.domain.interactor.living_room.usecase.LivingRoomTemperatureHumidityUseCase
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.model.LivingRoomPartialViewState
import com.chernysh.smarthome.domain.model.RoomPartialViewState
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
    private val livingRoomAquariumUseCase: LivingRoomAquariumUseCase,
    private val livingRoomLightsUseCase: LivingRoomLightsUseCase,
    private val schedulersTransformer: ObservableTransformer<Any, Any>) {

    fun onTemperatureHumidityObservable() = livingRoomTemperatureHumidityUseCase.getTemperatureHumidity()
            .compose(schedulersTransformer as ObservableTransformer<LivingRoomPartialViewState, LivingRoomPartialViewState>)

    fun getRozetkaStateObservable() =
        livingRoomRozetkaUseCase.processLivingRoomRozetka(LivingRoomRozetkaUseCase.Data(Method.GET))
                .compose(schedulersTransformer as ObservableTransformer<LivingRoomPartialViewState, LivingRoomPartialViewState>)

    fun enableRozetkaObservable() =
        livingRoomRozetkaUseCase.processLivingRoomRozetka(LivingRoomRozetkaUseCase.Data(Method.SET, true))
                .compose(schedulersTransformer as ObservableTransformer<LivingRoomPartialViewState, LivingRoomPartialViewState>)

    fun disableRozetkaObservable() =
        livingRoomRozetkaUseCase.processLivingRoomRozetka(LivingRoomRozetkaUseCase.Data(Method.SET, false))
                .compose(schedulersTransformer as ObservableTransformer<LivingRoomPartialViewState, LivingRoomPartialViewState>)

    fun enableAquariumObservable() =
        livingRoomAquariumUseCase.processLivingRoomAquarium(LivingRoomAquariumUseCase.Data(Method.SET, true))
            .compose(schedulersTransformer as ObservableTransformer<LivingRoomPartialViewState, LivingRoomPartialViewState>)

    fun disableAquariumObservable() =
        livingRoomAquariumUseCase.processLivingRoomAquarium(LivingRoomAquariumUseCase.Data(Method.SET, false))
            .compose(schedulersTransformer as ObservableTransformer<LivingRoomPartialViewState, LivingRoomPartialViewState>)

    fun getLightsStateObservable() = livingRoomLightsUseCase.processLivingRoomLights(LivingRoomLightsUseCase.Data(Method.GET))
        .compose(schedulersTransformer as ObservableTransformer<LivingRoomPartialViewState, LivingRoomPartialViewState>)

    fun enableLightsObservable() =
        livingRoomLightsUseCase.processLivingRoomLights(LivingRoomLightsUseCase.Data(Method.SET, true))
            .compose(schedulersTransformer as ObservableTransformer<LivingRoomPartialViewState, LivingRoomPartialViewState>)

    fun disableLightsObservable() =
        livingRoomLightsUseCase.processLivingRoomLights(LivingRoomLightsUseCase.Data(Method.SET, false))
            .compose(schedulersTransformer as ObservableTransformer<LivingRoomPartialViewState, LivingRoomPartialViewState>)
}