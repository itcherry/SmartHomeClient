package com.chernysh.smarthome.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricPrompt
import com.chernysh.smarthome.R
import com.chernysh.smarthome.domain.model.LoginViewState
import com.chernysh.smarthome.presentation.base.BaseActivity
import com.chernysh.smarthome.presentation.flat.FlatActivity
import com.chernysh.smarthome.utils.Notification
import com.chernysh.smarthome.utils.isFingerprintPromptAvailable
import com.chernysh.smarthome.utils.openActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.Executors

/**
 * Copyright 2020. Andrii Chernysh
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
    private var biometricPrompt: BiometricPrompt? = null
    private val fingerprintSubject: PublishSubject<Any> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupFingerprinting()
    }

    private fun setupFingerprinting() {
        if (applicationContext.isFingerprintPromptAvailable()) {
            ivFingerprint.visibility = View.VISIBLE
            ivFingerprint.setOnClickListener {
                openBiometricPrompt()
            }
            initBiometricPrompt()
            openBiometricPrompt()
        } else {
            ivFingerprint.visibility = View.GONE
        }
    }

    private fun initBiometricPrompt() {
        biometricPrompt = BiometricPrompt(this, Executors.newSingleThreadExecutor(),
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        biometricPrompt?.cancelAuthentication()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    fingerprintSubject.onNext(Notification.INSTANCE)
                }
            })
    }

    private fun openBiometricPrompt() {
        biometricPrompt?.authenticate(getPromptInfo())
    }

    private fun getPromptInfo() = BiometricPrompt.PromptInfo.Builder()
        .setTitle(getString(R.string.fingerprint_title))
        .setSubtitle(getString(R.string.fingerprint_subtitle))
        .setNegativeButtonText(getString(R.string.fingerprint_negative_button))
        .build()

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

    override fun fingerprintIntent(): Observable<Any> = fingerprintSubject

    override fun render(state: LoginViewState) {
        when (state) {
            // PIN
            is LoginViewState.LoadingPinState -> {
                doEnableButtons(false)
                renderLoading()
            }
            is LoginViewState.SuccessPinState -> {
                doEnableButtons(false)
                hideLoading()
                pinCode.renderSuccess()

                launchApp()
            }
            is LoginViewState.PasswordIncorrectState -> {
                hideLoading()
                doEnableButtons(false)
                pinCode.renderError()
            }
            is LoginViewState.ConnectivityPinErrorState -> {
                doEnableButtons(false)
                hideLoading()
                renderConnectivityError()
            }
            is LoginViewState.ErrorPinState -> {
                hideLoading()
                doEnableButtons(false)
                pinCode.renderError()
            }

            // Biometrics
            is LoginViewState.LoadingBiometricsState -> {
                doEnableButtons(false)
                renderBiometricsLoading()
            }
            is LoginViewState.SuccessBiometricsState -> {
                doEnableButtons(false)

                biometricPrompt = null
                launchApp()
            }
            is LoginViewState.ConnectivityBiometricsErrorState -> {
                doEnableButtons(false)
                hideBiometricsLoading()
                renderConnectivityError()
            }
            is LoginViewState.ErrorBiometricsState -> {
                hideBiometricsLoading()
                doEnableButtons(true)
                openBiometricPrompt()
            }
            is LoginViewState.EmptyState -> {
                doEnableButtons(true)
                hideBiometricsLoading()
                hideLoading()
                pinCode.clear()
            }
        }
    }

    private fun launchApp() {
        openActivity<FlatActivity> {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        overridePendingTransition(0, 0)
        finish()
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

    private fun renderBiometricsLoading() {
        pbBiometricsLoading.visibility = View.VISIBLE
        ivFingerprint.visibility = View.GONE
    }

    private fun hideBiometricsLoading() {
        ivFingerprint.visibility = View.VISIBLE
        pbBiometricsLoading.visibility = View.GONE
    }

    private fun renderConnectivityError() {
        showNetworkSnackbarError()
        pinCode.clear()
    }

    override fun onDestroy() {
        biometricPrompt?.cancelAuthentication()
        biometricPrompt = null
        super.onDestroy()
    }
}