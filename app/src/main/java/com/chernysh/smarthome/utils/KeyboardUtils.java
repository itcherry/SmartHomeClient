package com.chernysh.smarthome.utils;
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

import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import timber.log.Timber;

/**
 * Utility class to process keyboard actions
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */

public class KeyboardUtils {
    private static final String TAG = KeyboardUtils.class.getName();

    /**
     * Soft keyboard states
     */
    public enum KeyboardState {
        SHOW, HIDE, TOGGLE;
    }

    private KeyboardUtils() {
        // This utility class is not publicly instantiable
    }

    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            Timber.tag(TAG).e("InputMethodManager is null. Method : hideSoftInput.");
        }
    }

    public static void showSoftInput(@Nullable EditText edit, Context context) {
        if(edit != null) {
            edit.setFocusable(true);
            edit.setFocusableInTouchMode(true);
            edit.requestFocus();
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(edit, 0);
            } else {
                Timber.tag(TAG).e("InputMethodManager is null. Method : showSoftInput.");
            }
        } else {
            Timber.tag(TAG).e("Requested EditText is null. Method : showSoftInput.");
        }
    }

    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            Timber.tag(TAG).e("InputMethodManager is null. Method : toggleSoftInput.");
        }
    }
}
