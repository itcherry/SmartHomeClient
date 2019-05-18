package com.chernysh.smarthome.domain.interactor.login

import android.util.Log
import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.data.exception.SmartHomeApiException
import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.model.LoginViewState
import com.chernysh.smarthome.domain.repository.LoginRepository
import io.reactivex.Observable
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class LoginInteractor @Inject constructor(private val loginRepository: LoginRepository) {
    fun authUser(params: String): Observable<LoginViewState> =
        loginRepository.authUser(getHashedPassword(params), DataPolicy.API)
            .map<LoginViewState> { LoginViewState.SuccessState }
            .startWith(LoginViewState.LoadingState)
            .onErrorReturn {
                when (it) {
                    is NoConnectivityException -> LoginViewState.ConnectivityErrorState
                    is SmartHomeApiException -> if (it.serverErrorCode == INCORRECT_PASSWORD_ERROR_ID) {
                        LoginViewState.PasswordIncorrectState
                    } else {
                        LoginViewState.ErrorState(it)
                    }
                    else -> LoginViewState.ErrorState(it)
                }
            }

    private fun getHashedPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

    companion object {
        private const val INCORRECT_PASSWORD_ERROR_ID = 1
    }
}