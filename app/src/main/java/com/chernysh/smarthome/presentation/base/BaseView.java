package com.chernysh.smarthome.presentation.base;
/**
 * Copyright 2018. Andrii Chernysh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.EditText;
import com.chernysh.smarthome.utils.KeyboardUtils;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Base view interface, that describes methods
 * to show errors, empty lists or content.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         Developed by <u>Transcendensoft</u>
 */
public interface BaseView extends MvpView {
    // Base views
    void showSnackError(String message);

    void showSnackError(@StringRes int messageRes);

    void showSnackMessage(String message);

    void showSnackMessage(@StringRes int messageRes);

    void showShortToastMessage(String message);

    void showShortToastMessage(@StringRes int messageRes);

    void showLongToastMessage(String message);

    void showLongToastMessage(@StringRes int messageRes);

    void handleKeyboard(KeyboardUtils.KeyboardState state, @Nullable EditText editText);

    void showLoadingDialog();

    void hideLoadingDialog();
}
