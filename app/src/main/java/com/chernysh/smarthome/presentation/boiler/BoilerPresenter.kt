package com.chernysh.smarthome.presentation.boiler

import com.chernysh.smarthome.domain.interactor.boiler.BoilerInteractor
import com.chernysh.smarthome.domain.model.*
import com.chernysh.smarthome.presentation.base.BasePresenter
import com.chernysh.smarthome.presentation.bedroom.BedroomContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Copyright 2021. Andrii Chernysh
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
 * Implementation of boiler presenter.
 * Here are work with server to process boiler events
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BoilerPresenter @Inject constructor(private val boilerInteractor: BoilerInteractor) :
    BasePresenter<BoilerContract.View, BoilerViewState>(), BoilerContract.Presenter {

    @Override
    override fun bindIntents() {
        val boilerStateIntent = getBoilerStateIntent()
        val boilerScheduleStateIntent = getBoilerScheduleStateIntent()
        val saveBoilerScheduleIntent = getSaveBoilerScheduleIntent()
        val refreshDataIntent = getRefreshDataIntent()

        val allIntents = Observable.merge(
            boilerStateIntent,
            boilerScheduleStateIntent,
            saveBoilerScheduleIntent,
            refreshDataIntent
        )
        val initialState =
            BoilerViewState(
                BooleanViewState.LoadingState,
                BooleanViewState.LoadingState,
                BoilerScheduleViewState.LoadingState
            )
        val stateObservable = allIntents.scan(initialState, this::reducer)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(stateObservable, BoilerContract.View::render)
    }

    private fun getBoilerStateIntent() = intent(BoilerContract.View::setBoilerStateIntent)
        .debounce(200, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                boilerInteractor.enableBoilerObservable()
            } else {
                boilerInteractor.disableBoilerObservable()
            }
        }


    private fun getBoilerScheduleStateIntent() =
        intent(BoilerContract.View::setBoilerScheduleStateIntent)
            .debounce(200, TimeUnit.MILLISECONDS)
            .switchMap {
                if (it) {
                    boilerInteractor.enableBoilerScheduleObservable()
                } else {
                    boilerInteractor.disableBoilerScheduleObservable()
                }
            }

    private fun getSaveBoilerScheduleIntent() =
        intent(BoilerContract.View::saveBoilerScheduleIntent)
            .debounce(200, TimeUnit.MILLISECONDS)
            .switchMap {
                boilerInteractor.saveBoilerSchedule(it)
            }

    private fun getRefreshDataIntent() = viewResumedObservable
        .switchMap {
            Observable.zip(boilerInteractor.getBoilerEnabledStateObservable(),
                boilerInteractor.getBoilerScheduleEnabledStateObservable(),
                boilerInteractor.getBoilerScheduleObservable(),
                Function3 { boilerEnabledState: BoilerPartialViewState.BoilerEnabledState,
                            boilerScheduleEnabledState: BoilerPartialViewState.BoilerScheduleEnabledState,
                            boilerSchedule: BoilerPartialViewState.BoilerScheduleState ->
                    BoilerPartialViewState.AllDataState(
                        boilerEnabledState.state,
                        boilerScheduleEnabledState.state,
                        boilerSchedule.state
                    )
                })
        }

    private fun reducer(
        previousState: BoilerViewState,
        changes: BoilerPartialViewState
    ): BoilerViewState {
        return when (changes) {
            is BoilerPartialViewState.BoilerEnabledState -> previousState.copy(enabledState = changes.state)
            is BoilerPartialViewState.BoilerScheduleEnabledState -> previousState.copy(
                scheduleEnabledState = changes.state
            )
            is BoilerPartialViewState.BoilerScheduleState -> previousState.copy(schedule = changes.state)
            is BoilerPartialViewState.AllDataState -> BoilerViewState(
                changes.boilerEnabledState,
                changes.boilerScheduleEnabledState,
                changes.scheduleState
            )
        }
    }
}