package com.chernysh.smarthome.presentation.living_room

import android.os.Bundle
import com.chernysh.smarthome.databinding.ActivityLivingRoomBinding
import com.chernysh.smarthome.domain.model.LivingRoomViewState
import com.chernysh.smarthome.presentation.base.BaseActivity
import io.reactivex.Observable

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
 * Activity for displaying living room device states.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class LivingRoomActivity : BaseActivity<LivingRoomContract.View, LivingRoomPresenter>(),
    LivingRoomContract.View {
    private lateinit var binding: ActivityLivingRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLivingRoomBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fabMenu.setOnClickListener {
            onBackPressed()
        }
    }

    override fun setRozetkaStateIntent(): Observable<Boolean> = binding.evRozette.setElementStateIntent()

    override fun setAquariumStateIntent(): Observable<Boolean> = binding.evAquarium.setElementStateIntent()

    override fun setLightsStateIntent(): Observable<Boolean> = binding.evLights.setElementStateIntent()

    override fun render(state: LivingRoomViewState) {
        binding.cvTemperatureHumidityCard.render(state.temperatureHumidityViewState)
        binding.evRozette.render(state.rozetkaViewState)
        binding.evLights.render(state.lightsViewState)
        binding.evAquarium.render(state.aquariumViewState)
    }

}