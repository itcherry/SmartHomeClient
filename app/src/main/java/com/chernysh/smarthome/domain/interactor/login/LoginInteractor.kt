package com.chernysh.smarthome.domain.interactor.login

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.model.LoginViewState
import com.chernysh.smarthome.domain.repository.LoginRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class LoginInteractor @Inject constructor(private val loginRepository: LoginRepository) {
  fun authUser(params: String): Observable<LoginViewState> =
    loginRepository.authUser(params, DataPolicy.API)
      .toObservable()
      .map<LoginViewState> { LoginViewState.SuccessState }
      .startWith { LoginViewState.LoadingState }
      .onErrorReturn {
        if (it is NoConnectivityException) {
          LoginViewState.ConnectivityErrorState
        } else {
          LoginViewState.ErrorState(it)
        }
      }
}