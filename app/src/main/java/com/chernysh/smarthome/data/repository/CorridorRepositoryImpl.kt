package com.chernysh.smarthome.data.repository

import com.chernysh.smarthome.data.network.source.CorridorApiDataSource
import com.chernysh.smarthome.domain.repository.CorridorRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

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
 * Interface that represents a Repository (or Gateway)
 * for setting and getting Corridor rosettes and light states
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class CorridorRepositoryImpl @Inject constructor(
    private val corridorApiDataSource: CorridorApiDataSource
) : CorridorRepository {

    override fun setLightState(isEnabled: Boolean): Maybe<Boolean> = corridorApiDataSource.setLightState(isEnabled)

    override fun getLightState(): Single<Boolean> = corridorApiDataSource.getLightState()
}