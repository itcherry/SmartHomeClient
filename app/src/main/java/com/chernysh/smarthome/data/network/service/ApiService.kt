package com.chernysh.smarthome.data.network.service

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

import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.*

/**
 * Interface that describes all API methods with server
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
interface ApiService {
    /* Firebase */
    @PUT("user/{userId}/token")
    fun bindFirebaseToken(@Path("userId") userId: Long,
                          @Body tokenBody: Map<String, String>): Maybe<Any>

    @DELETE("user/{userId}/token")
    fun unbindFirebaseToken(@Path("userId") userId: Long): Maybe<Any>

    /* Alarm */
    @PUT("alarm")
    fun setAlarmState(@Query("isEnable") isEnable: Boolean): Maybe<Any>

    @GET("alarm")
    fun getAlarmState(): Single<Boolean>

    /* Boiler */
    @PUT("boiler")
    fun setBoilerState(@Query("isEnable") isEnable: Boolean): Maybe<Any>

    @GET("boiler")
    fun getBoilerState(): Single<Boolean>

    /* Door */
    @PUT("door")
    fun setDoorState(@Query("doOpen") doOpen: Boolean): Maybe<Any>

    @GET("door")
    fun getDoorState(): Single<Boolean>

    /* Neptun */
    @GET("neptun")
    fun getNeptunState(): Single<Boolean>

    /* Bedroom */
    @PUT("bedroom/rozetka")
    fun setBedroomRozetkaState(@Query("isEnable") isEnable: Boolean): Maybe<Any>

    @GET("bedroom/rozetka")
    fun getBedroomRozetkaState(): Single<Boolean>

    @PUT("bedroom/light")
    fun setBedroomLightState(@Query("isEnable") isEnable: Boolean): Maybe<Any>

    @GET("bedroom/light")
    fun getBedroomLightState(): Single<Boolean>

    /* Corridor */
    @PUT("corridor/light")
    fun setCorridorLightState(@Query("isEnable") isEnable: Boolean): Maybe<Any>

    @GET("corridor/light")
    fun getCorridorLightState(): Single<Boolean>

    /* Kitchen */
    @PUT("kitchen/light")
    fun setKitchenLightState(@Query("isEnable") isEnable: Boolean): Maybe<Any>

    @GET("kitchen/light")
    fun getKitchenLightState(): Single<Boolean>

    /* Living room */
    @PUT("living-room/rozetka")
    fun setLivingRoomRozetkaState(@Query("isEnable") isEnable: Boolean): Maybe<Any>

    @GET("living-room/rozetka")
    fun getLivingRoomRozetkaState(): Single<Boolean>
}
