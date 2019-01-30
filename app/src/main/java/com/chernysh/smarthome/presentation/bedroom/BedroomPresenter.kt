package com.chernysh.smarthome.presentation.bedroom

import com.chernysh.smarthome.domain.interactor.bedroom.BedroomInteractor
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.RoomPartialViewState
import com.chernysh.smarthome.domain.model.RoomViewState
import com.chernysh.smarthome.domain.model.TemperatureHumidityViewState
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
 * Here are work with server to process bedroom events
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BedroomPresenter @Inject constructor(private val bedroomInteractor: BedroomInteractor,
                                           schedulersTransformer: ObservableTransformer<Any, Any>) :
    BasePresenter<BedroomContract.View, RoomViewState>(schedulersTransformer) {

    @Override
    override fun bindIntents() {
        val lightsIntent = getChangeLightsStateIntent()
        val rozetkaIntent = getChangeRozetkaStateIntent()
        val temperatureIntent = getTemperatureIntent()

        val allIntents = Observable.merge(lightsIntent, rozetkaIntent, temperatureIntent)
        val initialState =
            RoomViewState(BooleanViewState.LoadingState, BooleanViewState.LoadingState, TemperatureHumidityViewState.NoDataState)
        val stateObservable = allIntents.scan(initialState, this::reducer)

        subscribeViewState(stateObservable, BedroomContract.View::render);
    }

    private fun getChangeLightsStateIntent() = intent(BedroomContract.View::setLightsStateIntent)
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                bedroomInteractor.enableLightsObservable()
            } else {
                bedroomInteractor.disableLightsObservable()
            }
        }
        .compose(applySchedulers())


    private fun getChangeRozetkaStateIntent() = intent(BedroomContract.View::setRozetkaStateIntent)
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                bedroomInteractor.enableRozetkaObservable()
            } else {
                bedroomInteractor.disableRozetkaObservable()
            }
        }
        .compose(applySchedulers())

    private fun getTemperatureIntent() = intent { viewResumedObservable }
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            bedroomInteractor.onTemperatureHumidityObservable()
        }
        .compose(applySchedulers())

    private fun reducer(previousState: RoomViewState, changes: RoomPartialViewState): RoomViewState {
        return when (changes) {
            is RoomPartialViewState.LightsState -> previousState.copy(lightsViewState = changes.state)
            is RoomPartialViewState.RozetkaState -> previousState.copy(rozetkaViewState = changes.state)
            is RoomPartialViewState.TemperatureHumidityState -> previousState.copy(temperatureHumidityViewState = changes.state)
        }
    }
}