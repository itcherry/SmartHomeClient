package com.chernysh.smarthome.presentation.login

import com.chernysh.smarthome.domain.interactor.living_room.LivingRoomInteractor
import com.chernysh.smarthome.domain.model.*
import com.chernysh.smarthome.presentation.base.BasePresenter
import com.chernysh.smarthome.presentation.living_room.LivingRoomContract
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
 * Implementation of login presenter.
 * Here are work with server to process login events
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class LoginPresenter @Inject constructor(/*private val livingRoomInteractor: LivingRoomInteractor*/,
                                              schedulersTransformer: ObservableTransformer<Any, Any>) :
    BasePresenter<LoginContract.View, LoginViewState>(schedulersTransformer) {

    @Override
    override fun bindIntents() {
        val validateLoginIntent = getValidateLoginStateIntent()
        val validatePasswordIntent = getValidatePasswordStateIntent()
        val submitLogin = getSubmitLoginStateIntent()

        val allIntents = Observable.merge(validateLoginIntent, validatePasswordIntent, submitLogin)

        subscribeViewState(allIntents, LoginContract.View::render);
    }

    private fun getValidateLoginStateIntent() = intent(LoginContract.View::validateLogin)
        .debounce(400, TimeUnit.MILLISECONDS)
        /*.switchMap {
            if (it) {
                livingRoomInteractor.enableRozetkaObservable()
            } else {
                livingRoomInteractor.disableRozetkaObservable()
            }
        }*/
        .compose(applySchedulers())

    private fun getValidatePasswordStateIntent() = intent(LoginContract.View::validatePassword)
        .debounce(400, TimeUnit.MILLISECONDS)
        /*.switchMap {
            if (it) {
                livingRoomInteractor.enableRozetkaObservable()
            } else {
                livingRoomInteractor.disableRozetkaObservable()
            }
        }*/
        .compose(applySchedulers())

    private fun getSubmitLoginStateIntent() = intent(LoginContract.View::submitIntent)
        .debounce(400, TimeUnit.MILLISECONDS)
        /*.switchMap {
            if (it) {
                livingRoomInteractor.enableRozetkaObservable()
            } else {
                livingRoomInteractor.disableRozetkaObservable()
            }
        }*/
        .compose(applySchedulers())
}