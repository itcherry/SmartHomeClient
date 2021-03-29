package com.chernysh.smarthome.presentation.boiler

import com.chernysh.smarthome.domain.interactor.bedroom.BedroomInteractor
import com.chernysh.smarthome.domain.model.*
import com.chernysh.smarthome.presentation.base.BasePresenter
import io.reactivex.Observable
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
class BoilerPresenter @Inject constructor(private val bedroomInteractor: BedroomInteractor) :
    BasePresenter<BoilerContract.View, BoilerScheduleViewState>(), BoilerContract.Presenter {

    @Override
    override fun bindIntents() {
        val submitTimeRangesIntent = getChangeLightsStateIntent()
        val refreshDataIntent = getRefreshDataIntent()

        val stateObservable = Observable.merge(submitTimeRangesIntent, refreshDataIntent)

        subscribeViewState(stateObservable, BoilerContract.View::render)
    }


    private fun getRefreshDataIntent() = viewResumedObservable
        .switchMap {
            Observable.zip(bedroomInteractor.getLightsStateObservable(), bedroomInteractor.getRozetkaStateObservable(),
                BiFunction { lightsState: RoomPartialViewState.LightsState, rozetkaState: RoomPartialViewState.RozetkaState ->
                    RoomPartialViewState.LightsAndRozetkaState(lightsState.state, rozetkaState.state)
                })
        }
}