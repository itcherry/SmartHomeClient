package com.chernysh.smarthome.presentation.living_room

import com.chernysh.smarthome.domain.interactor.living_room.LivingRoomInteractor
import com.chernysh.smarthome.domain.model.*
import com.chernysh.smarthome.presentation.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.functions.Function3
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Copyright 2020. Andrii Chernysh
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
 * Implementation of living room presenter.
 * Here are work with server to process living room events
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class LivingRoomPresenter @Inject constructor(private val livingRoomInteractor: LivingRoomInteractor) :
    BasePresenter<LivingRoomContract.View, LivingRoomViewState>(), LivingRoomContract.Presenter {

    @Override
    override fun bindIntents() {
        val rozetkaIntent = getChangeRozetkaStateIntent()
        val lightsIntent = getChangeLightsStateIntent()
        val aquariumIntent = getChangeAquariumStateIntent()
        val temperatureIntent = getTemperatureIntent()
        val refreshDataIntent = getRefreshDataIntent()

        val allIntents = Observable.merge(
            listOf(
                rozetkaIntent,
                lightsIntent,
                aquariumIntent,
                temperatureIntent,
                refreshDataIntent
            )
        )
        val initialState =
            LivingRoomViewState(
                BooleanViewState.LoadingState, BooleanViewState.LoadingState,
                BooleanViewState.LoadingState, TemperatureHumidityViewState.NoDataState
            )
        val stateObservable = allIntents.scan(initialState, this::reducer)

        subscribeViewState(stateObservable, LivingRoomContract.View::render);
    }

    private fun getChangeRozetkaStateIntent() =
        intent(LivingRoomContract.View::setRozetkaStateIntent)
            .debounce(200, TimeUnit.MILLISECONDS)
            .switchMap {
                if (it) {
                    livingRoomInteractor.enableRozetkaObservable()
                } else {
                    livingRoomInteractor.disableRozetkaObservable()
                }
            }

    private fun getChangeLightsStateIntent() =
        intent(LivingRoomContract.View::setLightsStateIntent)
            .debounce(200, TimeUnit.MILLISECONDS)
            .switchMap {
                if (it) {
                    livingRoomInteractor.enableLightsObservable()
                } else {
                    livingRoomInteractor.disableLightsObservable()
                }
            }

    private fun getChangeAquariumStateIntent() =
        intent(LivingRoomContract.View::setAquariumStateIntent)
            .debounce(200, TimeUnit.MILLISECONDS)
            .switchMap {
                if (it) {
                    livingRoomInteractor.enableAquariumObservable()
                } else {
                    livingRoomInteractor.disableAquariumObservable()
                }
            }

    private fun getTemperatureIntent() = intent { viewCreatedObservable }
        .debounce(200, TimeUnit.MILLISECONDS)
        .switchMap {
            livingRoomInteractor.onTemperatureHumidityObservable()
        }

    private fun getRefreshDataIntent() = viewCreatedObservable
        .switchMap {
            Observable.merge(
                livingRoomInteractor.getLightsStateObservable(),
                livingRoomInteractor.getRozetkaStateObservable(),
                livingRoomInteractor.getAquariumStateObservable()
            )
        }

    private fun reducer(
        previousState: LivingRoomViewState,
        changes: LivingRoomPartialViewState
    ): LivingRoomViewState {
        return when (changes) {
            is LivingRoomPartialViewState.RozetkaState -> previousState.copy(rozetkaViewState = changes.state)
            is LivingRoomPartialViewState.AquariumState -> previousState.copy(aquariumViewState = changes.state)
            is LivingRoomPartialViewState.LightsState -> previousState.copy(lightsViewState = changes.state)
            is LivingRoomPartialViewState.TemperatureHumidityState ->
                previousState.copy(temperatureHumidityViewState = changes.state)
        }
    }
}