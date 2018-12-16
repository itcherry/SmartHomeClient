package com.chernysh.smarthome.di.component

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

import com.chernysh.smarthome.SmartHomeApplication
import com.chernysh.smarthome.data.repository.RepositoryModule
import com.chernysh.smarthome.di.AppModule
import com.chernysh.smarthome.di.RxModule
import com.chernysh.smarthome.di.scope.ApplicationScope
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Component for Dagger 2 in order to create
 * Application level graph.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
@ApplicationScope
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class, RepositoryModule::class, RxModule::class]
)
interface AppComponent : AndroidInjector<SmartHomeApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SmartHomeApplication>()
}
