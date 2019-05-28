package com.chernysh.smarthome.domain.interactor.corridor

import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.FlatPartialViewState
import com.chernysh.smarthome.domain.model.Method
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * Interactor that manipulates data within corridor
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
@Suppress("UNCHECKED_CAST")
class CorridorInteractor @Inject constructor(private val corridorLightsUseCase: CorridorLightsUseCase,
                                             private val schedulersTransformer: ObservableTransformer<Any, Any>) {
    fun getLightsStateObservable() = corridorLightsUseCase.processCorridorLights(CorridorLightsUseCase.Data(Method.GET))
        .compose(schedulersTransformer as ObservableTransformer<BooleanViewState, BooleanViewState>)

    fun enableLightsObservable() =
        corridorLightsUseCase.processCorridorLights(CorridorLightsUseCase.Data(Method.SET, true))
            .compose(schedulersTransformer as ObservableTransformer<BooleanViewState, BooleanViewState>)

    fun disableLightsObservable() =
        corridorLightsUseCase.processCorridorLights(CorridorLightsUseCase.Data(Method.SET, false))
            .compose(schedulersTransformer as ObservableTransformer<BooleanViewState, BooleanViewState>)

}