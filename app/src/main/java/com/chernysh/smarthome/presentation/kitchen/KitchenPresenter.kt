package com.chernysh.smarthome.presentation.kitchen

import com.chernysh.smarthome.domain.interactor.kitchen.KitchenInteractor
import com.chernysh.smarthome.domain.model.*
import com.chernysh.smarthome.presentation.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.util.concurrent.TimeUnit
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
 * Implementation of bedroom presenter.
 * Here are work with server to process kitchen events
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class KitchenPresenter @Inject constructor(private val kitchenInteractor: KitchenInteractor,
                                           schedulersTransformer: ObservableTransformer<Any, Any>) :
    BasePresenter<KitchenContract.View, RoomWithoutRozetkaViewState>(schedulersTransformer) {

    @Override
    override fun bindIntents() {
        val lightsIntent = getChangeLightsStateIntent()
        val temperatureIntent = getTemperatureIntent()
        val refreshDataIntent = getRefreshDataIntent()

        val allIntents = Observable.merge(lightsIntent, temperatureIntent, refreshDataIntent)
        val initialState =
            RoomWithoutRozetkaViewState(BooleanViewState.LoadingState, TemperatureHumidityViewState.NoDataState)
        val stateObservable = allIntents.scan(initialState, this::reducer)

        subscribeViewState(stateObservable, KitchenContract.View::render);
    }

    private fun getChangeLightsStateIntent() = intent(KitchenContract.View::setLightsStateIntent)
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                kitchenInteractor.enableLightsObservable()
            } else {
                kitchenInteractor.disableLightsObservable()
            }
        }
        .compose(applySchedulers())


    private fun getTemperatureIntent() = intent { viewResumedObservable }
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            kitchenInteractor.onTemperatureHumidityObservable()
        }
        .compose(applySchedulers())

    private fun getRefreshDataIntent() = intent(KitchenContract.View::refreshDataIntent)
        .mergeWith(viewResumedObservable)
        .switchMap { kitchenInteractor.getLightsStateObservable() }
        .compose(applySchedulers())

    private fun reducer(previousState: RoomWithoutRozetkaViewState, changes: RoomPartialWithoutRozetkaViewState):
            RoomWithoutRozetkaViewState {
        return when (changes) {
            is RoomPartialWithoutRozetkaViewState.LightsState -> previousState.copy(lightsViewState = changes.state)
            is RoomPartialWithoutRozetkaViewState.TemperatureHumidityState ->
                previousState.copy(temperatureHumidityViewState = changes.state)
        }
    }
}