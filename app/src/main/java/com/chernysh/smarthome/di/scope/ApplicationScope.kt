package com.chernysh.smarthome.di.scope

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

import javax.inject.Qualifier

/**
 * Dagger 2 scope for application level
 * (like `@Singleton`). Additionally,
 * a component with a specific scope cannot have a
 * sub component with the same scope.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
@MustBeDocumented
@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope
