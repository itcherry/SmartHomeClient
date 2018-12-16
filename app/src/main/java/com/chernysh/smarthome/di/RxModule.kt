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

import com.chernysh.smarthome.di.qualifier.SchedulerComputation
import com.chernysh.smarthome.di.qualifier.SchedulerIO
import com.chernysh.smarthome.di.qualifier.SchedulerUI
import com.chernysh.smarthome.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Scheduler provider module
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
@Module
class RxModule {
    @ApplicationScope
    @SchedulerComputation
    @Provides
    fun provideComputationScheduler(): Scheduler {
        return Schedulers.computation()
    }

    @ApplicationScope
    @SchedulerIO
    @Provides
    fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @ApplicationScope
    @SchedulerUI
    @Provides
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @ApplicationScope
    @Provides
    fun provideObservableTransformer(@SchedulerIO ioScheduler: Scheduler, @SchedulerUI uiScheduler: Scheduler): ObservableTransformer<Any, Any> =
            ObservableTransformer{
                it.subscribeOn(ioScheduler)
                        .observeOn(uiScheduler)
            }

    @ApplicationScope
    @Provides
    fun provideCompletableTransformer(@SchedulerIO ioScheduler: Scheduler, @SchedulerUI uiScheduler: Scheduler): CompletableTransformer =
            CompletableTransformer {
                it.subscribeOn(ioScheduler)
                        .observeOn(uiScheduler)
            }

    @ApplicationScope
    @Provides
    fun provideSingleTransformer(@SchedulerIO ioScheduler: Scheduler, @SchedulerUI uiScheduler: Scheduler): SingleTransformer<*, *> =
            SingleTransformer<Any, Any> {
                it.subscribeOn(ioScheduler)
                        .observeOn(uiScheduler)
            }

    @ApplicationScope
    @Provides
    fun provideMaybeTransformer(@SchedulerIO ioScheduler: Scheduler, @SchedulerUI uiScheduler: Scheduler): MaybeTransformer<*, *> =
            MaybeTransformer<Any, Any> {
                it.subscribeOn(ioScheduler)
                        .observeOn(uiScheduler)
            }

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}
