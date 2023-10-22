package com.chernysh.smarthome.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricPrompt
import com.chernysh.smarthome.R
import com.chernysh.smarthome.databinding.ActivityLoginBinding
import com.chernysh.smarthome.domain.model.LoginViewState
import com.chernysh.smarthome.presentation.base.BaseActivity
import com.chernysh.smarthome.presentation.flat.FlatActivity
import com.chernysh.smarthome.utils.Notification
import com.chernysh.smarthome.utils.isFingerprintPromptAvailable
import com.chernysh.smarthome.utils.openActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
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
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setupFingerprinting(isPossibleToEnable: Boolean) {
        if (isPossibleToEnable && applicationContext.isFingerprintPromptAvailable()) {
            binding.ivFingerprint.visibility = View.VISIBLE
            binding.ivFingerprint.setOnClickListener {
                openBiometricPrompt()
            }
            initBiometricPrompt()
            openBiometricPrompt()
        } else {
            binding.ivFingerprint.visibility = View.GONE
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
        val btn1Observable = RxView.clicks(binding.btn1).map { "1" }
        val btn2Observable = RxView.clicks(binding.btn2).map { "2" }
        val btn3Observable = RxView.clicks(binding.btn3).map { "3" }
        val btn4Observable = RxView.clicks(binding.btn4).map { "4" }
        val btn5Observable = RxView.clicks(binding.btn5).map { "5" }
        val btn6Observable = RxView.clicks(binding.btn6).map { "6" }
        val btn7Observable = RxView.clicks(binding.btn7).map { "7" }
        val btn8Observable = RxView.clicks(binding.btn8).map { "8" }
        val btn9Observable = RxView.clicks(binding.btn9).map { "9" }
        val btn0Observable = RxView.clicks(binding.btn0).map { "0" }
        val btnBackspaceObservable = RxView.clicks(binding.btnBackspace).map { "" }

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
            .doOnNext { binding.pinCode.setText(it) }
    }

    override fun fingerprintIntent(): Observable<Any> = fingerprintSubject

    override fun render(state: LoginViewState) {
        when (state) {
            is LoginViewState.InitialState -> {
                setupFingerprinting(state.isFirebaseTokenBinded)
            }
            // PIN
            is LoginViewState.LoadingPinState -> {
                doEnableButtons(false)
                renderLoading()
            }

            is LoginViewState.SuccessPinState -> {
                doEnableButtons(false)
                hideLoading()
                binding.pinCode.renderSuccess()

                launchApp()
            }

            is LoginViewState.PasswordIncorrectState -> {
                hideLoading()
                doEnableButtons(false)
                binding.pinCode.renderError()
            }

            is LoginViewState.ConnectivityPinErrorState -> {
                doEnableButtons(false)
                hideLoading()
                renderConnectivityError()
            }

            is LoginViewState.ErrorPinState -> {
                hideLoading()
                doEnableButtons(false)
                binding.pinCode.renderError()
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
                binding.pinCode.clear()
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
        binding.btn0.isEnabled = doEnable
        binding.btn1.isEnabled = doEnable
        binding.btn2.isEnabled = doEnable
        binding.btn3.isEnabled = doEnable
        binding.btn4.isEnabled = doEnable
        binding.btn5.isEnabled = doEnable
        binding.btn6.isEnabled = doEnable
        binding.btn7.isEnabled = doEnable
        binding.btn8.isEnabled = doEnable
        binding.btn9.isEnabled = doEnable
        binding.btnBackspace.isEnabled = doEnable
    }

    private fun renderLoading() {
        binding.pbLoginLoading.visibility = View.VISIBLE
        binding.pinCode.isEnabled = false
    }

    private fun hideLoading() {
        binding.pbLoginLoading.visibility = View.INVISIBLE
        binding.pinCode.isEnabled = true
    }

    private fun renderBiometricsLoading() {
        binding.pbBiometricsLoading.visibility = View.VISIBLE
        binding.ivFingerprint.visibility = View.GONE
    }

    private fun hideBiometricsLoading() {
        binding.ivFingerprint.visibility = View.VISIBLE
        binding.pbBiometricsLoading.visibility = View.GONE
    }

    private fun renderConnectivityError() {
        showNetworkSnackbarError()
        binding.pinCode.clear()
    }

    override fun onDestroy() {
        biometricPrompt?.cancelAuthentication()
        biometricPrompt = null
        super.onDestroy()
    }
}