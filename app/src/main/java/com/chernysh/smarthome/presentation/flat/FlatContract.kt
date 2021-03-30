package com.chernysh.smarthome.presentation.flat

import com.chernysh.smarthome.domain.model.FlatViewState
import com.chernysh.smarthome.presentation.base.BaseView
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

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
 * View and Presenter interfaces contract for all
 * flat presentation
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
interface FlatContract {
    interface View : BaseView, MvpView {
        fun initDataIntent(): Observable<Boolean>
        fun acceptedAlarmIntent(): Observable<Boolean>
        fun setSecurityStateIntent(): Observable<Boolean>
        fun render(state: FlatViewState)

        fun openBedroomActivity(): Observable<Any>
        fun openKitchenActivity(): Observable<Any>
        fun openLivingRoomActivity(): Observable<Any>
        fun openCorridorActivity(): Observable<Any>
        fun openCameraActivity(): Observable<Any>
        fun openDanfossActivity(): Observable<Any>
        fun openFloorHeatingActivity(): Observable<Any>
        fun openAirConditionerActivity(): Observable<Any>
        fun openBoilerActivity(): Observable<Any>

        fun showAlarmDialog(): Observable<Any>
        fun reloadDataObservable(): Observable<Any>
    }

    interface Presenter

    companion object {
        const val DANFOSS_PACKAGE_NAME = "com.danfoss.danfosseco"
        const val TEPLOLUXE_PACKAGE_NAME = "com.SES.MCSClient"
        const val AIR_CONDITIONER_PACKAGE_NAME = "com.gree.ewpesmart"
    }
}