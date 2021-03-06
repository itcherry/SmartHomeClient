package com.chernysh.smarthome.presentation.flat

import com.chernysh.smarthome.di.scope.ActivityScope
import com.chernysh.smarthome.presentation.bedroom.BedroomContract
import com.chernysh.smarthome.presentation.bedroom.BedroomPresenter
import dagger.Binds
import dagger.Module

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
 * Module that provides fragments, presenters
 * and other instances for main flat activity presentations
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
@Module
interface FlatModule {
    @ActivityScope
    @Binds
    fun bindPresenter(flatPresenter: FlatPresenter): FlatContract.Presenter
}