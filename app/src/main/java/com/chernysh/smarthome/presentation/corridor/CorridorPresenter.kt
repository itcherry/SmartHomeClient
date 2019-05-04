package com.chernysh.smarthome.presentation.corridor

import com.chernysh.smarthome.domain.interactor.corridor.CorridorInteractor
import com.chernysh.smarthome.domain.model.BooleanViewState
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
 * Implementation of corridor presenter.
 * Here are work with server to process corridor events
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class CorridorPresenter @Inject constructor(private val corridorInteractor: CorridorInteractor,
                                           schedulersTransformer: ObservableTransformer<Any, Any>) :
    BasePresenter<CorridorContract.View, BooleanViewState>(schedulersTransformer), CorridorContract.Presenter {

    @Override
    override fun bindIntents() {
        val lightsIntent = getChangeLightsStateIntent()
        val refreshDataIntent = getRefreshDataIntent()

        val allIntents = Observable.merge(lightsIntent, refreshDataIntent)

        subscribeViewState(allIntents, CorridorContract.View::render);
    }

    private fun getChangeLightsStateIntent() = intent(CorridorContract.View::setLightsStateIntent)
        .debounce(200, TimeUnit.MILLISECONDS)
        .switchMap {
            if (it) {
                corridorInteractor.enableLightsObservable()
            } else {
                corridorInteractor.disableLightsObservable()
            }
        }
        .compose(applySchedulers())

    private fun getRefreshDataIntent() = viewResumedObservable
        .switchMap { corridorInteractor.getLightsStateObservable() }
        .compose(applySchedulers())
}