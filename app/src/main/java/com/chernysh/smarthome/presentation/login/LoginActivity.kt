package com.chernysh.smarthome.presentation.login

import android.os.Bundle
import android.view.View
import com.chernysh.smarthome.R
import com.chernysh.smarthome.domain.model.LoginViewState
import com.chernysh.smarthome.presentation.base.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
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

    return Observable.merge(listOf(btn1Observable, btn2Observable, btn3Observable,
      btn4Observable, btn5Observable, btn6Observable, btn7Observable,
      btn8Observable, btn9Observable, btn0Observable, btnBackspaceObservable))
      .scan { t1: String, t2: String -> if (t2.isBlank()) t1.dropLast(1) else t1 + t2 }
      .doOnNext { pinCode.setText(it) }
  }

  override fun render(state: LoginViewState) {
    when (state) {
      is LoginViewState.LoadingState -> renderLoading()
      is LoginViewState.SuccessState -> pinCode.renderSuccess()
      is LoginViewState.PasswordIncorrectState -> pinCode.renderError()
      is LoginViewState.ConnectivityErrorState -> renderConnectivityError()
      is LoginViewState.ErrorState -> pinCode.renderError()
    }
  }

  private fun renderLoading(){
    pbLoginLoading.visibility = View.VISIBLE
    pinCode.isEnabled = false
  }

  private fun renderConnectivityError() {
    showSnackError(R.string.network_error)
    pinCode.clear()
  }
}