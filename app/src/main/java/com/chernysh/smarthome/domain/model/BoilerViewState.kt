package com.chernysh.smarthome.domain.model

import com.chernysh.timerangepicker.TimeRange

/**
 * Copyright 2021. Andrii Chernysh
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
 * Class that describes view state of boiler, that may be:
 * - enabled/disabled
 * - have enabled/disabled schedule
 * - have pre-defined schedule
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
data class BoilerViewState(
    val enabledState: BooleanViewState,
    val scheduleEnabledState: BooleanViewState,
    val schedule: BoilerScheduleViewState
)