package com.chernysh.smarthome.presentation.flat

import com.chernysh.smarthome.domain.interactor.safety.SafetyInteractor
import com.chernysh.smarthome.domain.model.*
import com.chernysh.smarthome.presentation.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function4
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
 * Here are work with server to process flat main events
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class FlatPresenter @Inject constructor(private val safetyInteractor: SafetyInteractor,
                                        schedulersTransformer: ObservableTransformer<Any, Any>) :
    BasePresenter<FlatContract.View, FlatViewState>(schedulersTransformer) {

    @Override
    override fun bindIntents() {
        val safetyStateObservable = getSafetyStateObservable()
        val stateObservable = getFlatViewStateObservable(safetyStateObservable)

        subscribeViewState(stateObservable, FlatContract.View::render)
    }

    private fun getSafetyStateObservable(): Observable<FlatViewState.SafetyViewState> {
        val alarmIntent = getChangeAlarmStateIntent()
        val boilerIntent = getChangeBoilerStateIntent()
        val doorIntent = getChangeDoorStateIntent()
        val temperatureIntent = getTemperatureIntent()
        val refreshDataIntent = getRefreshDataIntent()

        val allIntents =
            Observable.merge(listOf(alarmIntent, boilerIntent, doorIntent, temperatureIntent, refreshDataIntent))
        val initialState =
            FlatViewState.SafetyViewState(
                BooleanViewState.LoadingState, BooleanViewState.LoadingState, BooleanViewState.LoadingState,
                BooleanViewState.LoadingState, TemperatureHumidityViewState.NoDataState
            )

        return allIntents.scan(initialState, this::reducer)
    }

    private fun getFlatViewStateObservable(
        safetyStateObservable: Observable<FlatViewState.SafetyViewState>): Observable<FlatViewState> {
        val showAlarmIntent = getShowAlarmDialogIntent()
        val openBedroomIntent = getOpenBedroomIntent()
        val openKitchenIntent = getOpenKitchenIntent()
        val openCorridorIntent = getOpenCorridorIntent()
        val openLivingRoomIntent = getOpenLivingRoomIntent()

        val stateObservable = Observable.merge(
            listOf(
                safetyStateObservable, showAlarmIntent, openBedroomIntent,
                openKitchenIntent, openCorridorIntent, openLivingRoomIntent
            )
        )

        return stateObservable
    }

    private fun getChangeAlarmStateIntent() = intent(FlatContract.View::acceptedAlarmIntent)
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                safetyInteractor.enableAlarmObservable()
            } else {
                safetyInteractor.disableAlarmObservable()
            }
        }
        .compose(applySchedulers())


    private fun getChangeBoilerStateIntent() = intent(FlatContract.View::setBoilerStateIntent)
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                safetyInteractor.enableBoilerObservable()
            } else {
                safetyInteractor.disableBoilerObservable()
            }
        }
        .compose(applySchedulers())

    private fun getChangeDoorStateIntent() = intent(FlatContract.View::setDoorStateIntent)
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                safetyInteractor.enableDoorObservable()
            } else {
                safetyInteractor.disableDoorObservable()
            }
        }
        .compose(applySchedulers())

    private fun getTemperatureIntent() = intent { viewResumedObservable }
        .debounce(400, TimeUnit.MILLISECONDS)
        .switchMap {
            safetyInteractor.onTemperatureHumidityObservable()
        }
        .compose(applySchedulers())

    private fun getRefreshDataIntent() = intent(FlatContract.View::refreshDataIntent)
        .mergeWith(viewResumedObservable)
        .switchMap {
            Observable.zip(safetyInteractor.getAlarmStateObservable(), safetyInteractor.getBoilerStateObservable(),
                safetyInteractor.getDoorStateObservable(), safetyInteractor.getNeptunStateObservable(),
                Function4 { alarmState: FlatPartialViewState.AlarmState, boilerState: FlatPartialViewState.BoilerState,
                            doorState: FlatPartialViewState.DoorState, neptunState: FlatPartialViewState.NeptunState ->
                    FlatPartialViewState.AllDevicesState(alarmState.state, boilerState.state, doorState.state, neptunState.state)
                })
        }
        .compose(applySchedulers())

    private fun reducer(previousState: FlatViewState.SafetyViewState, changes: FlatPartialViewState): FlatViewState.SafetyViewState {
        return when (changes) {
            is FlatPartialViewState.AlarmState -> previousState.copy(alarmViewState = changes.state)
            is FlatPartialViewState.BoilerState -> previousState.copy(boilerViewState = changes.state)
            is FlatPartialViewState.DoorState -> previousState.copy(doorViewState = changes.state)
            is FlatPartialViewState.NeptunState -> previousState.copy(neptunViewState = changes.state)
            is FlatPartialViewState.TemperatureHumidityState -> previousState.copy(temperatureHumidityOutsideViewState = changes.state)
            is FlatPartialViewState.AllDevicesState ->
                previousState.copy(
                    alarmViewState = changes.alarmState, boilerViewState = changes.boilerState,
                    doorViewState = changes.doorState, neptunViewState = changes.neptunState
                )
        }
    }

    private fun getShowAlarmDialogIntent() = intent(FlatContract.View::showAlarmDialog)
        .debounce(400, TimeUnit.MILLISECONDS)
        .compose(applySchedulers())
        .map { FlatViewState.ShowAlarmDialogClicked }

    private fun getOpenBedroomIntent() = intent(FlatContract.View::openBedroomActivity)
        .debounce(400, TimeUnit.MILLISECONDS)
        .compose(applySchedulers())
        .map { FlatViewState.BedroomClicked }

    private fun getOpenKitchenIntent() = intent(FlatContract.View::openKitchenActivity)
        .debounce(400, TimeUnit.MILLISECONDS)
        .compose(applySchedulers())
        .map { FlatViewState.KitchenClicked }

    private fun getOpenCorridorIntent() = intent(FlatContract.View::openCorridorActivity)
        .debounce(400, TimeUnit.MILLISECONDS)
        .compose(applySchedulers())
        .map { FlatViewState.CorridorClicked }

    private fun getOpenLivingRoomIntent() = intent(FlatContract.View::openLivingRoomActivity)
        .debounce(400, TimeUnit.MILLISECONDS)
        .compose(applySchedulers())
        .map { FlatViewState.LivingRoomClicked }
}