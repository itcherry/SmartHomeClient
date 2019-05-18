package com.chernysh.smarthome.presentation.base

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject

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
 * Presenter that contains all base fields and methods for all presenters
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
abstract class BasePresenter<V : MvpView?, VS> : MviBasePresenter<V, VS>() {
    private val onViewResumedObserver = PublishSubject.create<Any>()
    private val onViewCreatedObserver = PublishSubject.create<Any>()
    private val onViewDestroyedObserver = PublishSubject.create<Any>()
    private val onViewPausedObserver = PublishSubject.create<Any>()

    protected val viewCreatedObservable: Observable<Any> = onViewCreatedObserver
    protected val viewResumedObservable: Observable<Any> = onViewResumedObserver
    protected val viewPausedObservable: Observable<Any> = onViewPausedObserver
    protected val viewDestroyedObservable: Observable<Any> = onViewDestroyedObserver

    fun onCreate() {
        onViewCreatedObserver.onNext(Unit)
    }

    fun onDestroy() {
        onViewDestroyedObserver.onNext(Unit)
    }

    fun onViewResumed() {
        onViewResumedObserver.onNext(Unit)
    }

    fun onViewPaused() {
        onViewPausedObserver.onNext(Unit)
    }
}