package com.chernysh.smarthome.data.network.retrofit

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

import com.chernysh.smarthome.data.exception.SmartHomeApiException
import com.chernysh.smarthome.data.model.common.ServerError
import com.chernysh.smarthome.data.model.common.ServerResult
import com.chernysh.smarthome.data.model.common.ServerStatus
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.jetbrains.annotations.Nullable
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Retrofit factory that handles API errors;
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */

class ApiErrorHandlerFactory : Converter.Factory() {

    @Nullable
    override fun responseBodyConverter(
        type: Type?, annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {

            val envelopedType = TypeToken.getParameterized(ServerResult::class.java, type).type
            val delegate = retrofit!!.nextResponseBodyConverter<ServerResult<*>>(this, envelopedType, annotations!!)

            return Converter { body: ResponseBody ->
                if(body.contentLength() == 0L && body.contentType() == null){
                    null
                } else {
                    val serverResult = delegate.convert(body)

                    if (ServerStatus.getServerStatusBasedOnString(
                            serverResult?.status ?: "error"
                        ) == ServerStatus.ERROR
                    ) {
                        throw SmartHomeApiException(serverResult?.serverError ?: ServerError())
                    }

                    serverResult?.data
                }
            }

    }

    companion object {
        fun create(): Converter.Factory {
            return ApiErrorHandlerFactory()
        }
    }
}
