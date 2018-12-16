package com.chernysh.smarthome.data.model.common

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

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Generic class, that describes fields which device
 * can receive from server.
 * There are 3 basic fields:
 * 1) ServerStatus - [ServerStatus] can be success or error
 * 2) Error message_received - message_received to output or null if success
 * 3) Data - result object from server
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
class ServerResult<T> {
    @SerializedName("status")
    @Expose
    val status: String? = null

    @SerializedName("error")
    @Expose
    val serverError: ServerError? = null

    @SerializedName("data")
    @Expose
    val data: T? = null
}
