package com.chernysh.smarthome.presentation.login

import com.chernysh.smarthome.BuildConfig
import com.chernysh.smarthome.domain.interactor.login.LoginInteractor
import com.chernysh.smarthome.domain.model.*
import com.chernysh.smarthome.presentation.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
class LoginPresenter @Inject constructor(private val loginInteractor: LoginInteractor) :
    BasePresenter<LoginContract.View, LoginViewState>(), LoginContract.Presenter {

    @Override
    override fun bindIntents() {
        subscribeViewState(getSubmitLoginStateIntent(), LoginContract.View::render);
    }

    private fun getSubmitLoginStateIntent() = Observable.merge(
        intent(LoginContract.View::pinCodeIntent).map { it to false },
        intent(LoginContract.View::fingerprintIntent).map { BuildConfig.PIN to true }
    )
        .filter { it.first.length == PIN_CODE_LENGTH }
        .switchMap { (pin, isFromFingerprint) ->
            loginInteractor.authUser(pin, isFromFingerprint)
        }
}