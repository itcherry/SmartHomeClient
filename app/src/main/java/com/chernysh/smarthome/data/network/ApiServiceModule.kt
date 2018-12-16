package com.chernysh.smarthome.data.network

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

import com.chernysh.smarthome.data.network.retrofit.HedbanzApiErrorHandlerFactory
import com.chernysh.smarthome.data.network.service.ApiService
import com.chernysh.smarthome.data.network.source.ApiDataSource
import com.chernysh.smarthome.di.scope.ApplicationScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Module that provides all API services
 * and Retrofit instances
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
@Module(includes = arrayOf(NetworkModule::class))
class ApiServiceModule {
    @Provides
    @ApplicationScope
    fun provideApiService(apiRetrofit: Retrofit): ApiService {
        return apiRetrofit.create<ApiService>(ApiService::class.java)
    }

    @Provides
    @ApplicationScope
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(HedbanzApiErrorHandlerFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(ApiDataSource.BASE_URL)
            .build()
    }
}
