package com.chernysh.smarthome.data.network.source

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


import com.chernysh.smarthome.BuildConfig
import com.chernysh.smarthome.data.network.service.ApiService

import javax.inject.Inject

/**
 * Manager to get entity API data source.
 * Contains common string constants for API purposes.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
abstract class ApiDataSource protected constructor() {
    @Inject lateinit var service: ApiService

    companion object {
        val HOST = BuildConfig.HOST_LINK
        val BASE_URL = HOST + BuildConfig.PORT_API

        val PORT_SOCKET = BuildConfig.PORT_SOCKET
        val SOCKET_NSP = "raspberry"
    }
}
