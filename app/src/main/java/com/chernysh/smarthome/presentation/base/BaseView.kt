package com.chernysh.smarthome.presentation.base

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

import androidx.annotation.StringRes
import android.widget.EditText
import com.chernysh.smarthome.utils.KeyboardUtils
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Base view interface, that describes methods
 * to show errors, empty lists or content.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
interface BaseView : MvpView {
    // Base views
    fun showNetworkSnackbarError(action: () -> Unit)

    fun showNetworkSnackbarError()

    fun showServerSnackbarError(action: () -> Unit)

    fun showServerSnackbarError()

    fun showSnackMessage(message: String)

    fun showSnackMessage(@StringRes messageRes: Int)

    fun showShortToastMessage(message: String)

    fun showShortToastMessage(@StringRes messageRes: Int)

    fun showLongToastMessage(message: String)

    fun showLongToastMessage(@StringRes messageRes: Int)

    fun handleKeyboard(state: KeyboardUtils.KeyboardState, editText: EditText?)

    fun showLoadingDialog()

    fun hideLoadingDialog()
}
