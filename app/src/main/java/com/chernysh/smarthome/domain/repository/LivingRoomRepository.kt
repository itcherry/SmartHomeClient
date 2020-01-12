package com.chernysh.smarthome.domain.repository

import io.reactivex.Maybe
import io.reactivex.Single

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

/**
 * Interface that represents a Repository for
 * getting and setting states of light, rosettes, etc.
 * in living room
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
interface LivingRoomRepository {
    fun setRozetkaState(isEnabled: Boolean): Maybe<Boolean>
    fun getRozetkaState(): Single<Boolean>

    fun setLightState(isEnabled: Boolean): Maybe<Boolean>
    fun getLightState(): Single<Boolean>

    fun setAquariumState(isEnabled: Boolean): Maybe<Boolean>
    fun getAquariumState(): Single<Boolean>
}