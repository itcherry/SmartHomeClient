package com.chernysh.smarthome.domain.interactor.kitchen

import com.chernysh.smarthome.domain.interactor.kitchen.usecase.KitchenLightsUseCase
import com.chernysh.smarthome.domain.interactor.kitchen.usecase.KitchenTemperatureHumidityUseCase
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.model.RoomPartialWithoutLightsViewState
import com.chernysh.smarthome.domain.model.RoomPartialWithoutRozetkaViewState
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * Interactor that manipulates data within bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
@Suppress("UNCHECKED_CAST")
class KitchenInteractor @Inject constructor(private val kitchenLightsUseCase: KitchenLightsUseCase,
                                            private val kitchenTemperatureHumidityUseCase: KitchenTemperatureHumidityUseCase,
                                            private val schedulersTransformer: ObservableTransformer<Any, Any>) {
    fun onTemperatureHumidityObservable() = kitchenTemperatureHumidityUseCase.getTemperatureHumidity()
            .compose(schedulersTransformer as ObservableTransformer<RoomPartialWithoutRozetkaViewState, RoomPartialWithoutRozetkaViewState>)

    fun getLightsStateObservable() = kitchenLightsUseCase.processKitchenLights(KitchenLightsUseCase.Data(Method.GET))
            .compose(schedulersTransformer as ObservableTransformer<RoomPartialWithoutRozetkaViewState, RoomPartialWithoutRozetkaViewState>)

    fun enableLightsObservable() =
        kitchenLightsUseCase.processKitchenLights(KitchenLightsUseCase.Data(Method.SET, true))
                .compose(schedulersTransformer as ObservableTransformer<RoomPartialWithoutRozetkaViewState, RoomPartialWithoutRozetkaViewState>)

    fun disableLightsObservable() =
        kitchenLightsUseCase.processKitchenLights(KitchenLightsUseCase.Data(Method.SET, false))
                .compose(schedulersTransformer as ObservableTransformer<RoomPartialWithoutRozetkaViewState, RoomPartialWithoutRozetkaViewState>)
}