package com.chernysh.smarthome.utils.logging

/**
 * Copyright 2017. Andrii Chernysh
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

import com.chernysh.smarthome.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import timber.log.Timber

/**
 * Module for providing logging instances.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
@Module
class LoggingModule {
    @Provides
    @ApplicationScope
    fun provideDebugTimberTree(): Timber.DebugTree {
        return Timber.DebugTree()
    }

    @Provides
    @ApplicationScope
    fun provideReleaseTimberTree(): CrashReportingTree {
        return CrashReportingTree()
    }
}
