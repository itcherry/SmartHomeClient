package com.chernysh.smarthome.data.exception

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

import com.chernysh.smarthome.data.model.common.ServerError

/**
 * Exception throw by the application when a there
 * was returned error from Hedbanz API.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */

class SmartHomeApiException : RuntimeException {
    private var mServerError: ServerError? = null

    val serverErrorMessage: String
        get() = if (mServerError == null) {
            "Error object from API is NULL!"
        } else {
            mServerError?.errorMessage ?: "No message provided from API"
        }

    val serverErrorCode: Int
        get() = if (mServerError == null) {
            -1
        } else {
            mServerError?.errorCode ?: -1
        }

    constructor(mServerError: ServerError) {
        this.mServerError = mServerError
    }

    constructor(message: String) : super(message) {
        mServerError = ServerError()
        mServerError?.errorMessage = message
        mServerError?.errorCode = -1
    }
}
