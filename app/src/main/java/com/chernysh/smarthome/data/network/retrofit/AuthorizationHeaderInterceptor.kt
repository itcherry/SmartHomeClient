package com.chernysh.smarthome.data.network.retrofit

import android.preference.PreferenceManager
import com.chernysh.smarthome.data.source.UserDataSource
import com.chernysh.smarthome.di.scope.ApplicationScope
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


/**
 * Copyright 2017. Andrii Chernysh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
/**
 * Interceptor that adds authorization token to header
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         Developed by <u>Transcendensoft</u>
 */
@ApplicationScope
class AuthorizationHeaderInterceptor @Inject constructor(
    private val userDataSource: UserDataSource
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (userDataSource.getToken().isNullOrBlank()) {
            chain.proceed(chain.request())
        } else {
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${userDataSource.getToken()}")
                .build()
            chain.proceed(newRequest)
        }
    }

}