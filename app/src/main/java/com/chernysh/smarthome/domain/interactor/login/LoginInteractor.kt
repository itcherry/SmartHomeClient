package com.chernysh.smarthome.domain.interactor.login

import android.util.Log
import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.data.exception.SmartHomeApiException
import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.model.LoginViewState
import com.chernysh.smarthome.domain.repository.LoginRepository
import com.chernysh.smarthome.utils.hash
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.mindrot.jbcrypt.BCrypt
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class LoginInteractor @Inject constructor(private val loginRepository: LoginRepository,
                                          private val schedulersTransformer: ObservableTransformer<Any, Any>) {

    @Suppress("UNCHECKED_CAST")
    fun authUser(params: String): Observable<LoginViewState> =
            loginRepository.authUser(params.hash(), DataPolicy.API)
                    .map<LoginViewState> { LoginViewState.SuccessState }
                    .startWith(LoginViewState.LoadingState)
                    .onErrorResumeNext { throwable: Throwable ->
                        Observable.concat(
                                Observable.just(getErrorLoginViewState(throwable)),
                                Observable.just(LoginViewState.EmptyState).delay(2, TimeUnit.SECONDS))
                    }
                    .compose(schedulersTransformer as ObservableTransformer<LoginViewState, LoginViewState>)


    private fun getErrorLoginViewState(throwable: Throwable): LoginViewState {
        return when (throwable) {
            is NoConnectivityException -> LoginViewState.ConnectivityErrorState
            is SmartHomeApiException -> if (throwable.serverErrorCode == INCORRECT_PASSWORD_ERROR_ID) {
                LoginViewState.PasswordIncorrectState
            } else {
                LoginViewState.ErrorState(throwable)
            }
            else -> LoginViewState.ErrorState(throwable)
        }
    }

    companion object {
        private const val INCORRECT_PASSWORD_ERROR_ID = 1
    }
}