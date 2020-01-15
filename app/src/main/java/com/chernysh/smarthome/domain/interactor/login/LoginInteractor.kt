package com.chernysh.smarthome.domain.interactor.login

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.data.exception.SmartHomeApiException
import com.chernysh.smarthome.data.prefs.SmartHomePreferences
import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.interactor.firebase.FirebaseTokenInteractor
import com.chernysh.smarthome.domain.model.LoginViewState
import com.chernysh.smarthome.domain.repository.LoginRepository
import com.chernysh.smarthome.utils.hash
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class LoginInteractor @Inject constructor(
    private val loginRepository: LoginRepository,
    private val firebaseTokenInteractor: FirebaseTokenInteractor,
    private val preferences: SmartHomePreferences,
    private val schedulersTransformer: ObservableTransformer<Any, Any>
) {

    @Suppress("UNCHECKED_CAST")
    fun authUser(params: String, isFromFingerprint: Boolean): Observable<LoginViewState> =
        Observable.concat(
            loginRepository.authUser(params.hash(), DataPolicy.API),
            getFirebaseTokenObservable(isFromFingerprint)
        )
            .map<LoginViewState> { getSuccessState(isFromFingerprint) }
            .startWith(getLoadingState(isFromFingerprint))
            .onErrorResumeNext { throwable: Throwable ->
                Observable.concat(
                    Observable.just(getErrorLoginViewState(throwable, isFromFingerprint)),
                    Observable.just(LoginViewState.EmptyState).delay(2, TimeUnit.SECONDS)
                )
            }.compose(schedulersTransformer as ObservableTransformer<LoginViewState, LoginViewState>)

    private fun getFirebaseTokenObservable(isFromFingerprint: Boolean): Observable<Any>? {
        return if (!preferences.getFirebaseTokenBinded()) {
            firebaseTokenInteractor.bindFirebaseId(preferences.getFirebaseToken())
                .doOnError { Timber.e("Token hasn't been binded") }
                .onErrorResumeNext(Observable.just(getSuccessState(isFromFingerprint)))
        } else {
            Observable.empty()
        }
    }

    private fun getErrorLoginViewState(
        throwable: Throwable,
        isFromFingerprint: Boolean
    ): LoginViewState {
        return when (throwable) {
            is NoConnectivityException -> getConnectivityErrorState(isFromFingerprint)
            is SmartHomeApiException -> if (throwable.serverErrorCode == INCORRECT_PASSWORD_ERROR_ID) {
                LoginViewState.PasswordIncorrectState
            } else {
                getErrorState(throwable, isFromFingerprint)
            }
            else -> getErrorState(throwable, isFromFingerprint)
        }
    }

    private fun getSuccessState(isFromFingerprint: Boolean) =
        if (isFromFingerprint) LoginViewState.SuccessBiometricsState else LoginViewState.SuccessPinState

    private fun getLoadingState(isFromFingerprint: Boolean) =
        if (isFromFingerprint) LoginViewState.LoadingBiometricsState else LoginViewState.LoadingPinState

    private fun getConnectivityErrorState(isFromFingerprint: Boolean) =
        if (isFromFingerprint)
            LoginViewState.ConnectivityBiometricsErrorState
        else
            LoginViewState.ConnectivityPinErrorState

    private fun getErrorState(throwable: Throwable, isFromFingerprint: Boolean) =
        if (isFromFingerprint)
            LoginViewState.ErrorBiometricsState(throwable)
        else
            LoginViewState.ErrorPinState(throwable)

    companion object {
        private const val INCORRECT_PASSWORD_ERROR_ID = 1
    }
}