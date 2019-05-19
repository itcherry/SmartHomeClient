package com.chernysh.smarthome.presentation.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.chernysh.smarthome.R
import com.chernysh.smarthome.domain.model.LoginViewState
import com.chernysh.smarthome.presentation.base.BaseActivity
import com.chernysh.smarthome.presentation.flat.FlatActivity
import com.chernysh.smarthome.utils.Notification
import com.jakewharton.rxbinding2.view.RxView
import com.readystatesoftware.chuck.internal.ui.MainActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_login.*

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
 * Activity for displaying login fields
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class LoginActivity : BaseActivity<LoginContract.View, LoginPresenter>(), LoginContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun pinCodeIntent(): Observable<String> {
        val btn1Observable = RxView.clicks(btn1).map { "1" }
        val btn2Observable = RxView.clicks(btn2).map { "2" }
        val btn3Observable = RxView.clicks(btn3).map { "3" }
        val btn4Observable = RxView.clicks(btn4).map { "4" }
        val btn5Observable = RxView.clicks(btn5).map { "5" }
        val btn6Observable = RxView.clicks(btn6).map { "6" }
        val btn7Observable = RxView.clicks(btn7).map { "7" }
        val btn8Observable = RxView.clicks(btn8).map { "8" }
        val btn9Observable = RxView.clicks(btn9).map { "9" }
        val btn0Observable = RxView.clicks(btn0).map { "0" }
        val btnBackspaceObservable = RxView.clicks(btnBackspace).map { "" }

        return Observable.merge(
                listOf(
                        btn1Observable, btn2Observable, btn3Observable,
                        btn4Observable, btn5Observable, btn6Observable, btn7Observable,
                        btn8Observable, btn9Observable, btn0Observable, btnBackspaceObservable
                )
        )
                .scan { t1: String, t2: String ->
                    when {
                        t2.isBlank() -> t1.dropLast(1)
                        t1.length < PIN_CODE_LENGTH -> t1 + t2
                        else -> t2
                    }
                }
                .doOnNext { pinCode.setText(it) }
    }

    override fun render(state: LoginViewState) {
        when (state) {
            is LoginViewState.LoadingState -> {
                doEnableButtons(false)
                renderLoading()
            }
            is LoginViewState.SuccessState -> {
                doEnableButtons(false)
                hideLoading()
                pinCode.renderSuccess()

                startActivity(Intent(this, FlatActivity::class.java))
                overridePendingTransition(0, 0)
            }
            is LoginViewState.PasswordIncorrectState -> {
                hideLoading()
                doEnableButtons(false)
                pinCode.renderError()
            }
            is LoginViewState.ConnectivityErrorState -> {
                doEnableButtons(false)
                hideLoading()
                renderConnectivityError()
            }
            is LoginViewState.ErrorState -> {
                hideLoading()
                doEnableButtons(false)
                pinCode.renderError()
            }
            is LoginViewState.EmptyState -> {
                doEnableButtons(true)
                hideLoading()
                pinCode.clear()
            }
        }
    }

    private fun doEnableButtons(doEnable: Boolean) {
        btn0.isEnabled = doEnable
        btn1.isEnabled = doEnable
        btn2.isEnabled = doEnable
        btn3.isEnabled = doEnable
        btn4.isEnabled = doEnable
        btn5.isEnabled = doEnable
        btn6.isEnabled = doEnable
        btn7.isEnabled = doEnable
        btn8.isEnabled = doEnable
        btn9.isEnabled = doEnable
        btnBackspace.isEnabled = doEnable
    }

    private fun renderLoading() {
        pbLoginLoading.visibility = View.VISIBLE
        pinCode.isEnabled = false
    }

    private fun hideLoading() {
        pbLoginLoading.visibility = View.INVISIBLE
        pinCode.isEnabled = true
    }


    private fun renderConnectivityError() {
        showNetworkSnackbarError()
        pinCode.clear()
    }
}