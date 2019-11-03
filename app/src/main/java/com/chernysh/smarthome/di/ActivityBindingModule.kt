package com.chernysh.smarthome.di

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

import com.chernysh.smarthome.data.network.service.firebase.FirebaseInstanceIdModule
import com.chernysh.smarthome.data.network.service.firebase.FirebaseMessagingServiceModule
import com.chernysh.smarthome.data.network.service.firebase.SmartHomeFirebaseInstanceIdService
import com.chernysh.smarthome.data.network.service.firebase.SmartHomeFirebaseMessagingService
import com.chernysh.smarthome.di.scope.ActivityScope
import com.chernysh.smarthome.di.scope.ServiceScope
import com.chernysh.smarthome.presentation.bedroom.BedroomActivity
import com.chernysh.smarthome.presentation.bedroom.BedroomModule
import com.chernysh.smarthome.presentation.corridor.CorridorActivity
import com.chernysh.smarthome.presentation.corridor.CorridorModule
import com.chernysh.smarthome.presentation.flat.FlatActivity
import com.chernysh.smarthome.presentation.flat.FlatModule
import com.chernysh.smarthome.presentation.kitchen.KitchenActivity
import com.chernysh.smarthome.presentation.kitchen.KitchenModule
import com.chernysh.smarthome.presentation.living_room.LivingRoomActivity
import com.chernysh.smarthome.presentation.living_room.LivingRoomModule
import com.chernysh.smarthome.presentation.login.LoginActivity
import com.chernysh.smarthome.presentation.login.LoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Dagger-Android module that binds all needed activities
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
@Module
interface ActivityBindingModule {

    /* Activities injection */
    @ActivityScope
    @ContributesAndroidInjector(modules = [FlatModule::class])
    fun flatActivity(): FlatActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [BedroomModule::class])
    fun bedroomActivity(): BedroomActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [CorridorModule::class])
    fun corridorActivity(): CorridorActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [KitchenModule::class])
    fun kitchenActivity(): KitchenActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LivingRoomModule::class])
    fun livingRoomActivity(): LivingRoomActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    fun loginActivity(): LoginActivity


    /* Services injection */
    @ServiceScope
    @ContributesAndroidInjector(modules = [FirebaseInstanceIdModule::class])
    fun firebaseInstanceIdService(): SmartHomeFirebaseInstanceIdService

    @ServiceScope
    @ContributesAndroidInjector(modules = [FirebaseMessagingServiceModule::class])
    fun firebaseMessagingService(): SmartHomeFirebaseMessagingService
}
