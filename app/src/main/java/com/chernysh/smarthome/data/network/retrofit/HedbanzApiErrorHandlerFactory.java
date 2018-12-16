package com.chernysh.smarthome.data.network.retrofit;
/**
 * Copyright 2017. Andrii Chernysh
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

import com.google.gson.reflect.TypeToken;
import com.transcendensoft.hedbanz.data.models.common.ServerResult;
import com.transcendensoft.hedbanz.data.models.common.ServerStatus;
import com.transcendensoft.hedbanz.data.exception.HedbanzApiException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Retrofit factory that handles API errors;
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         Developed by <u>Transcendensoft</u>
 */

public class HedbanzApiErrorHandlerFactory extends Converter.Factory {
    public HedbanzApiErrorHandlerFactory() {
    }

    public static Converter.Factory create(){
        return new HedbanzApiErrorHandlerFactory();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {
        Type envelopedType = TypeToken.getParameterized(ServerResult.class, type).getType();
        final Converter<ResponseBody, ServerResult<?>> delegate =
                retrofit.nextResponseBodyConverter(this, envelopedType, annotations);

        return body -> {
            ServerResult<?> serverResult = delegate.convert(body);

            if(ServerStatus.getServerStatusBasedOnString(
                    serverResult.getStatus()) == ServerStatus.ERROR){
                throw new HedbanzApiException(serverResult.getServerError());
            }

            return serverResult.getData();
        };
    }
}
