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

import com.chernysh.smarthome.data.model.UserDto
import io.reactivex.Maybe
import io.reactivex.Observable
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
    @PUT("user/token/{fcmToken}")
    fun bindFirebaseToken(@Path("fcmToken") token: String): Maybe<Any>

    /* Alarm */
    @PUT("alarm")
    fun setAlarmState(@Query("isEnable") isEnable: Boolean): Maybe<Boolean>

    @GET("alarm")
    fun getAlarmState(): Single<Boolean>

    /* Boiler */
    @PUT("boiler")
    fun setBoilerState(@Query("isEnable") isEnable: Boolean): Maybe<Boolean>

    @GET("boiler")
    fun getBoilerState(): Single<Boolean>

    /* Neptun */
    @GET("neptun")
    fun getNeptunState(): Single<Boolean>

    /* Bedroom */
    @PUT("bedroom/rozetka")
    fun setBedroomRozetkaState(@Query("isEnable") isEnable: Boolean): Maybe<Boolean>

    @GET("bedroom/rozetka")
    fun getBedroomRozetkaState(): Single<Boolean>

    @PUT("bedroom/light")
    fun setBedroomLightState(@Query("isEnable") isEnable: Boolean): Maybe<Boolean>

    @GET("bedroom/light")
    fun getBedroomLightState(): Single<Boolean>

    /* Corridor */
    @PUT("corridor/light")
    fun setCorridorLightState(@Query("isEnable") isEnable: Boolean): Maybe<Boolean>

    @GET("corridor/light")
    fun getCorridorLightState(): Single<Boolean>

    /* Kitchen */
    @PUT("kitchen/light")
    fun setKitchenLightState(@Query("isEnable") isEnable: Boolean): Maybe<Boolean>

    @GET("kitchen/light")
    fun getKitchenLightState(): Single<Boolean>

    /* Living room */
    @PUT("living-room/rozetka")
    fun setLivingRoomRozetkaState(@Query("isEnable") isEnable: Boolean): Maybe<Boolean>

    @GET("living-room/rozetka")
    fun getLivingRoomRozetkaState(): Single<Boolean>

    @PUT("living-room/light")
    fun setLivingRoomLightState(@Query("isEnable") isEnable: Boolean): Maybe<Boolean>

    @GET("living-room/light")
    fun getLivingRoomLightState(): Single<Boolean>

    @PUT("living-room/aquarium")
    fun setLivingRoomAquariumState(@Query("isEnable") isEnable: Boolean): Maybe<Boolean>

    @GET("living-room/aquarium")
    fun getLivingRoomAquariumState(): Single<Boolean>

    /* Login */
    @POST("user")
    fun authUser(@Body user: UserDto): Observable<UserDto>

    /* Security */
    @GET("security")
    fun isSecurityEnabled(): Single<Boolean>

    @PUT("security")
    fun setSecurityEnabled(@Query("doEnable") doEnable: Boolean): Maybe<Boolean>

    @GET("fire")
    fun isFireAtHome(): Single<Boolean>
}
