package ua.andrii.chernysh.rxsockets.data.network

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

import com.chernysh.smarthome.BuildConfig
import com.chernysh.smarthome.data.network.retrofit.HostSelectionInterceptor
import com.chernysh.smarthome.data.network.source.ApiDataSource
import com.chernysh.smarthome.di.scope.ApplicationScope
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import timber.log.Timber
import ua.andrii.chernysh.kotlinrxsocket.socket.RxSocket
import ua.andrii.chernysh.kotlinrxsocket.socket.SocketLoggingInterceptor
import ua.andrii.chernysh.kotlinrxsocket.socket.createRxSocket
import java.net.HttpURLConnection
import java.net.URL

/**
 * Module that provides all API and socket services
 * and Retrofit instances
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
@Module
class SocketServiceModule {
    @Provides
    @ApplicationScope
    fun provideSocket(gsonProvided: Gson, loggingInterceptor: SocketLoggingInterceptor, selectionInterceptor: HostSelectionInterceptor): RxSocket {
        return createRxSocket {
            hostIp = selectionInterceptor.getHost()?.let { "http://$it" } ?: ApiDataSource.HOST
            port = ApiDataSource.PORT_SOCKET.toInt()
            namespace = ApiDataSource.SOCKET_NSP
            socketLoggingInterceptor = loggingInterceptor
            gson = gsonProvided
            options {
                forceNew = false
                reconnection = true
            }
        }
    }

    @Provides
    @ApplicationScope
    fun provideSocketLoggingInterceptor(): SocketLoggingInterceptor {
        return object : SocketLoggingInterceptor {
            override fun logInfo(message: String) {
                Timber.i(message)
            }

            override fun logError(message: String) {
                Timber.e(message)
            }
        }
    }
}
