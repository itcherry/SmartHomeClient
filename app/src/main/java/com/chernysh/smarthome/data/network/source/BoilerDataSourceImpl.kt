package com.chernysh.smarthome.data.network.source

import com.chernysh.smarthome.data.source.BoilerDataSource
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
 * Data source for changing and getting state of boiler
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BoilerDataSourceImpl @Inject constructor() :
    ApiDataSource(), BoilerDataSource {
    override fun setState(isEnabled: Boolean): Maybe<Any> = service.setBoilerState(isEnabled)

    override fun getState(): Single<Boolean> = service.getBoilerState()
}