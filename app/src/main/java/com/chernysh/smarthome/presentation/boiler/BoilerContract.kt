package com.chernysh.smarthome.presentation.boiler

import com.chernysh.smarthome.domain.model.BoilerScheduleViewState
import com.chernysh.smarthome.domain.model.BoilerViewState
import com.chernysh.smarthome.presentation.base.BaseView
import com.chernysh.timerangepicker.TimeRange
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

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
 * View and Presenter interfaces contract for
 * boiler presentation
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
interface BoilerContract {
    interface View : BaseView, MvpView {
        fun saveBoilerScheduleIntent(): Observable<List<TimeRange>>
        fun setBoilerStateIntent(): Observable<Boolean>
        fun setBoilerScheduleStateIntent(): Observable<Boolean>
        fun render(state: BoilerViewState)
    }

    interface Presenter
}
