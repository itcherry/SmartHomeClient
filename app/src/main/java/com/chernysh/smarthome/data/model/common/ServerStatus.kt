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

/**
 * Describes status, that device can receive from server
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
enum class ServerStatus private constructor(status: String) {
    SUCCESS("success"), ERROR("error");

    var status: String
        internal set

    init {
        this.status = status
    }

    companion object {
        fun getServerStatusBasedOnString(status: String): ServerStatus {
            for (serverStatus in ServerStatus.values()) {
                if (serverStatus.status.equals(status, ignoreCase = true)) {
                    return serverStatus
                }
            }
            return ERROR
        }
    }
}
