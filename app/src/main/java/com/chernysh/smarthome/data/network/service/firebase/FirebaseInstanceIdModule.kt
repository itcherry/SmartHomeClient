package com.transcendensoft.hedbanz.data.network.service.firebase

import android.content.Context
import com.transcendensoft.hedbanz.di.qualifier.ServiceContext
import dagger.Binds
import dagger.Module

/**
 * Copyright 2017. Andrii Chernysh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
/**
 * Module that provides context and other
 * instances for firebase instance id service.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         Developed by <u>Transcendensoft</u>
 */
@Module
interface FirebaseInstanceIdModule {
    @ServiceContext
    @Binds
    fun bindServiceContext(hedbanzFirebaseInstanceIdService: HedbanzFirebaseInstanceIdService): Context
}