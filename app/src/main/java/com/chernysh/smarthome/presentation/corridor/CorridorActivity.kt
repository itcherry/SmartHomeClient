package com.chernysh.smarthome.presentation.corridor

import android.os.Bundle
import com.chernysh.smarthome.databinding.ActivityCorridorBinding
import com.chernysh.smarthome.domain.model.BooleanViewState
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
 * Activity for displaying corridor device states.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class CorridorActivity: BaseActivity<CorridorContract.View, CorridorPresenter>(), CorridorContract.View {
    private lateinit var binding: ActivityCorridorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorridorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fabMenu.setOnClickListener {
            onBackPressed()
        }
    }

    override fun setLightsStateIntent(): Observable<Boolean> = binding.evLight.setElementStateIntent()


    override fun render(state: BooleanViewState) {
        binding.evLight.render(state)
    }
}