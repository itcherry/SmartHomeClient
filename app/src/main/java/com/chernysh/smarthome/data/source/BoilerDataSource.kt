package com.chernysh.smarthome.data.source

import com.chernysh.timerangepicker.TimeRange
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
 * Getting and setting boiler state in data sources
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
interface BoilerDataSource {
    fun setState(isEnabled: Boolean): Maybe<Boolean>
    fun getState(): Single<Boolean>
    fun setScheduleState(isEnabled: Boolean): Maybe<Boolean>
    fun getScheduleState(): Single<Boolean>
    fun setSchedule(timeRanges: List<TimeRange>): Maybe<Any>
    fun getSchedule(): Single<List<TimeRange>>
}