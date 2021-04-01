package com.chernysh.smarthome.presentation.flat

import com.chernysh.smarthome.domain.interactor.safety.SafetyInteractor
import com.chernysh.smarthome.domain.model.*
import com.chernysh.smarthome.presentation.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
import io.reactivex.functions.Function5
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
 * Implementation of flat presenter.
 * Here are work with server to process flat main events
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class FlatPresenter @Inject constructor(private val safetyInteractor: SafetyInteractor) :
    BasePresenter<FlatContract.View, FlatViewState>(), FlatContract.Presenter {

    @Override
    override fun bindIntents() {
        val safetyStateObservable = getSafetyStateObservable()
        val stateObservable = getFlatViewStateObservable(safetyStateObservable)

        subscribeViewState(stateObservable, FlatContract.View::render)
    }

    private fun getSafetyStateObservable(): Observable<FlatViewState.SafetyViewState> {
        val alarmIntent = getChangeAlarmStateIntent()
        val securityIntent = getChangeSecurityStateIntent()
        val temperatureIntent = getTemperatureIntent()
        val refreshDataIntent = getRefreshDataIntent()

        val allIntents =
            Observable.merge(
                listOf(
                    alarmIntent,
                    securityIntent,
                    temperatureIntent,
                    refreshDataIntent
                )
            )
        val initialState =
            FlatViewState.SafetyViewState(
                BooleanViewState.LoadingState,
                BooleanViewState.LoadingState,
                BooleanViewState.LoadingState,
                BooleanViewState.LoadingState,
                TemperatureHumidityViewState.NoDataState
            )

        return allIntents.scan(initialState, this::reducer)
    }

    private fun getFlatViewStateObservable(
        safetyStateObservable: Observable<FlatViewState.SafetyViewState>
    ): Observable<FlatViewState> {
        val showAlarmIntent = getShowAlarmDialogIntent()
        val showSecurityIntent = getShowSecurityDialogIntent()
        val openBedroomIntent = getOpenBedroomIntent()
        val openKitchenIntent = getOpenKitchenIntent()
        val openCorridorIntent = getOpenCorridorIntent()
        val openCameraIntent = getOpenCameraIntent()
        val openDanfossIntent = getOpenDanfossIntent()
        val openFloorHeatingIntent = getOpenFloorHeatingIntent()
        val openAirConditionerIntent = getOpenAirConditionerIntent()

        val openLivingRoomIntent = getOpenLivingRoomIntent()
        val openBoilerIntent = getOpenBoilerIntent()
        val viewPausedIntent = viewPausedIntent()

        val stateObservable = Observable.merge(
            listOf(
                safetyStateObservable, showAlarmIntent, showSecurityIntent, openBedroomIntent,
                openKitchenIntent, openCorridorIntent, openLivingRoomIntent,
                openCameraIntent, openDanfossIntent, openFloorHeatingIntent,
                openAirConditionerIntent, openBoilerIntent, viewPausedIntent
            )
        ).observeOn(AndroidSchedulers.mainThread())

        return stateObservable
    }

    private fun getChangeAlarmStateIntent() = intent(FlatContract.View::acceptedAlarmIntent)
        .debounce(200, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                safetyInteractor.enableAlarmObservable()
            } else {
                safetyInteractor.disableAlarmObservable()
            }
        }

    private fun getChangeSecurityStateIntent() = intent(FlatContract.View::acceptedSecurityIntent)
        .debounce(200, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                safetyInteractor.enableSecurityObservable()
            } else {
                safetyInteractor.disableSecurityObservable()
            }
        }

    private fun getTemperatureIntent() = viewResumedObservable
        .delay(500L, TimeUnit.MILLISECONDS)
        .switchMap { safetyInteractor.onTemperatureHumidityObservable() }

    private fun getRefreshDataIntent() =
        Observable.merge(
            intent(FlatContract.View::initDataIntent),
            intent(FlatContract.View::reloadDataObservable)
        )
            .switchMap { allDevicesStateObservable() }

    private fun allDevicesStateObservable() =
        Observable.zip(safetyInteractor.getAlarmStateObservable(),
            safetyInteractor.getSecurityStateObservable(),
            safetyInteractor.getFireStateObservable(),
            safetyInteractor.getNeptunStateObservable(),
            Function4 { alarmState: FlatPartialViewState.AlarmState, securityState: FlatPartialViewState.SecurityState,
                             fireState: FlatPartialViewState.FireState, neptunState: FlatPartialViewState.NeptunState ->
                FlatPartialViewState.AllDevicesState(
                    alarmState.state,
                    neptunState.state,
                    securityState.state,
                    fireState.state
                )
            })

    private fun reducer(
        previousState: FlatViewState.SafetyViewState,
        changes: FlatPartialViewState
    ): FlatViewState.SafetyViewState {
        return when (changes) {
            is FlatPartialViewState.AlarmState -> previousState.copy(alarmViewState = changes.state)
            is FlatPartialViewState.SecurityState -> previousState.copy(securityViewState = changes.state)
            is FlatPartialViewState.FireState -> previousState.copy(fireViewState = changes.state)
            is FlatPartialViewState.NeptunState -> previousState.copy(neptunViewState = changes.state)
            is FlatPartialViewState.TemperatureHumidityState -> previousState.copy(
                temperatureHumidityOutsideViewState = changes.state
            )
            is FlatPartialViewState.AllDevicesState ->
                previousState.copy(
                    alarmViewState = changes.alarmState, securityViewState = changes.securityState,
                    fireViewState = changes.fireState, neptunViewState = changes.neptunState
                )
            is FlatPartialViewState.EmptyState -> previousState
        }
    }

    private fun getShowAlarmDialogIntent() = intent(FlatContract.View::showAlarmDialog)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map { FlatViewState.ShowAlarmDialogClicked }

    private fun getShowSecurityDialogIntent() = intent(FlatContract.View::showSecurityDialog)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map { FlatViewState.ShowSecurityDialogClicked }

    private fun getOpenBedroomIntent() = intent(FlatContract.View::openBedroomActivity)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map { FlatViewState.BedroomClicked }

    private fun getOpenKitchenIntent() = intent(FlatContract.View::openKitchenActivity)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map { FlatViewState.KitchenClicked }

    private fun getOpenCorridorIntent() = intent(FlatContract.View::openCorridorActivity)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map { FlatViewState.CorridorClicked }

    private fun getOpenCameraIntent() = intent(FlatContract.View::openCameraActivity)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map { FlatViewState.CameraClicked }

    private fun getOpenDanfossIntent() = intent(FlatContract.View::openDanfossActivity)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map { FlatViewState.DanfossClicked }

    private fun getOpenFloorHeatingIntent() = intent(FlatContract.View::openFloorHeatingActivity)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map { FlatViewState.FloorHeatingClicked }

    private fun getOpenAirConditionerIntent() =
        intent(FlatContract.View::openAirConditionerActivity)
            .debounce(200, TimeUnit.MILLISECONDS)
            .map { FlatViewState.AirConditionerClicked }

    private fun getOpenLivingRoomIntent() = intent(FlatContract.View::openLivingRoomActivity)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map { FlatViewState.LivingRoomClicked }

    private fun getOpenBoilerIntent() = intent(FlatContract.View::openBoilerActivity)
        .debounce(200, TimeUnit.MILLISECONDS)
        .map { FlatViewState.BoilerClicked }

    private fun viewPausedIntent() = viewPausedObservable
        .map { FlatViewState.NoActionsState }
}