package com.chernysh.smarthome.presentation.bedroom

import com.chernysh.smarthome.di.qualifier.SchedulerIO
import com.chernysh.smarthome.di.qualifier.SchedulerUI
import com.chernysh.smarthome.domain.interactor.bedroom.BedroomInteractor
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.Scheduler
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
                                           @SchedulerIO private val ioScheduler: Scheduler,
                                           @SchedulerUI private val uiScheduler: Scheduler) :
    MviBasePresenter<BedroomContract.View, BooleanViewState>() {

    @Override
    override fun bindIntents() {
        setLightsIntent()
        setRozetkaIntent()
    }

    private fun setLightsIntent() {
        val lightsIntent: Observable<BooleanViewState> = intent(BedroomContract.View::setLightsStateIntent)
            .subscribeOn(ioScheduler)
            .debounce(400, TimeUnit.MILLISECONDS)
            .switchMap {
                if (it) {
                    bedroomInteractor.enableLightsObservable()
                } else {
                    bedroomInteractor.disableLightsObservable()
                }
            }
            .observeOn(uiScheduler)

        subscribeViewState(lightsIntent, BedroomContract.View::renderLight)
    }

    private fun setRozetkaIntent() {
        val rozetkaIntent: Observable<BooleanViewState> = intent(BedroomContract.View::setRozetkaStateIntent)
            .subscribeOn(ioScheduler)
            .debounce(400, TimeUnit.MILLISECONDS)
            .switchMap {
                if (it) {
                    bedroomInteractor.enableRozetkaObservable()
                } else {
                    bedroomInteractor.disableRozetkaObservable()
                }
            }
            .observeOn(uiScheduler)

        subscribeViewState(rozetkaIntent, BedroomContract.View::renderRozetka)
    }
}