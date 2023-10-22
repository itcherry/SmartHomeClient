package com.chernysh.smarthome.utils.logging

/**
 * Copyright 2017. Andrii Chernysh
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

import android.util.Log


import timber.log.Timber

/**
 * Crash reporting tree from Timber example here
 * https://github.com/JakeWharton/timber/blob/master/timber-sample/src/main/java/com/example/timber/ExampleApp.java
 *
 * When not debug version all crashes goes to Crashlytics
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        // TODO replace Crashlytics with Firebase
        /*Crashlytics.log(priority, tag, message)

        if (t != null) {
            if (priority == Log.ERROR) {
                Crashlytics.logException(t)
            } else if (priority == Log.WARN) {
                Crashlytics.log(t.message)
            }
        }*/
    }
}
