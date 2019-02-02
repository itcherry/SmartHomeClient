package com.chernysh.smarthome.presentation.living_room

import com.chernysh.smarthome.domain.interactor.living_room.LivingRoomInteractor
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
 * Here are work with server to process bedroom events
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class LivingRoomPresenter @Inject constructor(private val livingRoomInteractor: LivingRoomInteractor,
                                              schedulersTransformer: ObservableTransformer<Any, Any>) :
    BasePresenter<LivingRoomContract.View, RoomWithoutLightsViewState>(schedulersTransformer) {

    @Override
    override fun bindIntents() {
        val rozetkaIntent = getChangeRozetkaStateIntent()
        val temperatureIntent = getTemperatureIntent()
        val refreshDataIntent = getRefreshDataIntent()

        val allIntents = Observable.merge(rozetkaIntent, temperatureIntent, refreshDataIntent)
        val initialState =
            RoomWithoutLightsViewState(BooleanViewState.LoadingState, TemperatureHumidityViewState.NoDataState)
        val stateObservable = allIntents.scan(initialState, this::reducer)

        subscribeViewState(stateObservable, LivingRoomContract.View::render);
    }

    private fun getChangeRozetkaStateIntent() = intent(LivingRoomContract.View::setRozetkaStateIntent)
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                livingRoomInteractor.enableRozetkaObservable()
            } else {
                livingRoomInteractor.disableRozetkaObservable()
            }
        }
        .compose(applySchedulers())

    private fun getTemperatureIntent() = intent { viewResumedObservable }
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            livingRoomInteractor.onTemperatureHumidityObservable()
        }
        .compose(applySchedulers())

    private fun getRefreshDataIntent() = intent(LivingRoomContract.View::refreshDataIntent)
        .mergeWith(viewResumedObservable)
        .switchMap { livingRoomInteractor.getRozetkaStateObservable() }
        .compose(applySchedulers())

    private fun reducer(previousState: RoomWithoutLightsViewState,
                        changes: RoomPartialWithoutLightsViewState): RoomWithoutLightsViewState {
        return when (changes) {
            is RoomPartialWithoutLightsViewState.RozetkaState -> previousState.copy(rozetkaViewState = changes.state)
            is RoomPartialWithoutLightsViewState.TemperatureHumidityState ->
                previousState.copy(temperatureHumidityViewState = changes.state)
        }
    }
}