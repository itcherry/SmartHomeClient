package com.chernysh.smarthome.domain.model

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
 * Class that describes view state of flat main devices
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
sealed class FlatViewState{
    data class SafetyViewState(val alarmViewState: BooleanViewState,
                               val boilerViewState: BooleanViewState,
                               val doorViewState: BooleanViewState,
                               val neptunViewState: BooleanViewState,
                               val temperatureHumidityOutsideViewState: TemperatureHumidityViewState): FlatViewState()
    object BedroomClicked: FlatViewState()
    object KitchenClicked: FlatViewState()
    object CorridorClicked: FlatViewState()
    object LivingRoomClicked: FlatViewState()
    object ShowAlarmDialogClicked: FlatViewState()
}