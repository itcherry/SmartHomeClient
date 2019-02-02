package com.chernysh.smarthome.domain.model

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
 * Class that describes view state of login activity.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
sealed class LoginViewState {
    object LoadingState : LoginViewState()
    object SuccessState : LoginViewState()
    object LoginNotValidState: LoginViewState()
    object PasswordNotValidState: LoginViewState()
    object NoSuchLoginState: LoginViewState()
    object PasswordIncorrectState: LoginViewState()
    data class ErrorState(val error: Throwable) : LoginViewState()
    object ConnectivityErrorState : LoginViewState()
}