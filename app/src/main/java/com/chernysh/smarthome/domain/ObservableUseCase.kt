package com.chernysh.smarthome.domain

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

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import ua.andrii.chernysh.rxsockets.domain.UseCase

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 *
 * This use case executes some command with
 * [Observable] boundaries
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */
abstract class ObservableUseCase<T, PARAM>(private val mSchedulersTransformer: ObservableTransformer<Any, Any>,
                                           compositeDisposable: CompositeDisposable) : UseCase(compositeDisposable) {

    /**
     * Builds an Observable which will be
     * used when executing the current [UseCase].
     */
    protected abstract fun buildUseCaseObservable(params: PARAM): Observable<T>

    /**
     * Executes the current use case.
     */
    fun execute(disposableObserver: DisposableObserver<T>?, params: PARAM) {
        if (disposableObserver == null) {
            throw NullPointerException(NULL_DISPOSABLE_OBSERVER_MSG)
        }
        val observable = this.buildUseCaseObservable(params)
            .compose(applySchedulers())

        addDisposable(observable.subscribeWith(disposableObserver))
    }

    /**
     * Executes the current use case.
     */
    fun execute(params: PARAM, onNext: (t: T) -> Unit = {},
                onError: (t: Throwable) -> Unit = {},
                onComplete: () -> Unit = {},
                onSubscribe: (t: Disposable) -> Unit = {},
                doOnNext: (t: T) -> Unit = {}) {
        val observable = this.buildUseCaseObservable(params)
            .doOnNext(doOnNext)
            .compose(applySchedulers())

        addDisposable(observable.subscribe(onNext, onError, onComplete, onSubscribe))
    }


    @SuppressWarnings("unchecked")
    private fun <S> applySchedulers(): ObservableTransformer<S, S> {
        return mSchedulersTransformer as ObservableTransformer<S, S>
    }
}
