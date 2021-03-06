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
 * Object in [ServerResult] which contains
 * string error message_received and integer error code.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
class ServerError (
    @SerializedName("errorMessage")
    @Expose
    var errorMessage: String? = null,

    @SerializedName("errorCode")
    @Expose
    var errorCode: Int = 0
)
