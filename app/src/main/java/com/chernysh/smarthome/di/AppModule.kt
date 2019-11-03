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

import android.app.Application
import android.content.Context
import com.chernysh.smarthome.SmartHomeApplication
import com.chernysh.smarthome.data.network.ApiServiceModule
import com.chernysh.smarthome.di.qualifier.ApplicationContext
import dagger.Binds
import dagger.Module
import dagger.Provides
import ua.andrii.chernysh.rxsockets.data.network.SocketServiceModule
import com.chernysh.smarthome.data.prefs.SmartHomePreferences
import com.chernysh.smarthome.di.scope.ApplicationScope


/**
 * Dagger 2 module for DiplomaApplication
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
@Module(includes = [SocketServiceModule::class, ApiServiceModule::class, ActivityBindingModule::class])
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindContext(smartHomeApplication: SmartHomeApplication): Context

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideApplication(smartHomeApplication: SmartHomeApplication): Application {
            return smartHomeApplication
        }

        @Provides
        @ApplicationScope
        @JvmStatic
        fun providePreferenceManger(@ApplicationContext context: Context): SmartHomePreferences {
            return SmartHomePreferences(context)
        }
    }
}
