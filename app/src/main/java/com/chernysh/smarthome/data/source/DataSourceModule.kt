package com.chernysh.smarthome.data.source

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

import com.chernysh.smarthome.data.network.source.*
import com.chernysh.smarthome.di.scope.ApplicationScope
import com.transcendensoft.hedbanz.data.source.FirebaseIdDataSource
import dagger.Binds
import dagger.Module

/**
 * Dagger 2 module to provide different sockets and DB data sources.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
@Module
interface DataSourceModule {
    @Binds
    @ApplicationScope
    fun provideTemperatureHumiditySocketDataSource(
        dataSource: TemperatureHumiditySocketDataSource): TemperatureHumidityDataSource

    @Binds
    @ApplicationScope
    fun provideAlarmApiDataSource(dataSource: AlarmApiDataSource): AlarmDataSource

    @Binds
    @ApplicationScope
    fun provideBedroomApiDataSource(dataSource: BedroomApiDataSource): BedroomDataSource

    @Binds
    @ApplicationScope
    fun provideBoilerApiDataSource(dataSource: BoilerApiDataSource): BoilerDataSource

    @Binds
    @ApplicationScope
    fun provideCorridorApiDataSource(dataSource: CorridorApiDataSource): CorridorDataSource

    @Binds
    @ApplicationScope
    fun provideDoorApiDataSource(dataSource: DoorApiDataSource): DoorDataSource

    @Binds
    @ApplicationScope
    fun provideKitchenApiDataSource(dataSource: KitchenApiDataSource): KitchenDataSource

    @Binds
    @ApplicationScope
    fun provideLivingRoomApiDataSource(dataSource: LivingRoomApiDataSource): LivingRoomDataSource

    @Binds
    @ApplicationScope
    fun provideNeptunApiDataSource(dataSource: NeptunApiDataSource): NeptunDataSource

    @Binds
    @ApplicationScope
    fun provideFirebaseIdApiDataSource(dataSource: FirebaseIdApiDataSource): FirebaseIdDataSource
}
