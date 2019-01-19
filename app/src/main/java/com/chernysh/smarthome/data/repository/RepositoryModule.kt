package com.chernysh.smarthome.data.repository

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

import com.chernysh.smarthome.data.source.DataSourceModule
import com.chernysh.smarthome.di.scope.ApplicationScope
import com.chernysh.smarthome.domain.repository.*
import dagger.Binds
import dagger.Module


/**
 * Module that binds repository dependencies
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
@Module(includes = [DataSourceModule::class])
interface RepositoryModule {
    @Binds
    @ApplicationScope
    fun bindTemperatureHumidityRepository(temperatureHumidityRepository: TemperatureHumidityRepositoryImpl): TemperatureHumidityRepository

    @Binds
    @ApplicationScope
    fun provideAlarmRepository(repository: AlarmRepositoryImpl): AlarmRepository

    @Binds
    @ApplicationScope
    fun provideBedroomRepository(repository: BedroomRepositoryImpl): BedroomRepository

    @Binds
    @ApplicationScope
    fun provideBoilerRepository(repository: BoilerRepositoryImpl): BoilerRepository

    @Binds
    @ApplicationScope
    fun provideCorridorRepository(repository: CorridorRepositoryImpl): CorridorRepository

    @Binds
    @ApplicationScope
    fun provideDoorRepository(repository: DoorRepositoryImpl): DoorRepository

    @Binds
    @ApplicationScope
    fun provideKitchenRepository(repository: KitchenRepositoryImpl): KitchenRepository

    @Binds
    @ApplicationScope
    fun provideLivingRoomRepository(repository: LivingRoomRepositoryImpl): LivingRoomRepository

    @Binds
    @ApplicationScope
    fun provideNeptunRepository(repository: NeptunRepositoryImpl): NeptunRepository

    @Binds
    @ApplicationScope
    fun provideFirebaseIdRepositorye(repository: FirebaseIdRepositoryImpl): FirebaseIdRepository
}
