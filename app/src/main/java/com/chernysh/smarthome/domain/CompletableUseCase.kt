package com.chernysh.smarthome.domain

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

import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.disposables.CompositeDisposable
import ua.andrii.chernysh.rxsockets.domain.UseCase

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * This use case executes some command with
 * [Completable] boundaries
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
abstract class CompletableUseCase<PARAM>(private val mCompletableTransformer: CompletableTransformer,
                                         mCompositeDisposable: CompositeDisposable) : UseCase(mCompositeDisposable) {

    /**
     * Builds an Observable which will be
     * used when executing the current [UseCase].
     */
    protected abstract fun buildUseCaseCompletable(params: PARAM): Completable

    /**
     * Executes the current use case.
     */
    fun execute(params: PARAM, onComplete: () -> Unit = {},
                onError: (err: Throwable) -> Unit = {}) {
        val completable = this.buildUseCaseCompletable(params)
            .compose(mCompletableTransformer)

        addDisposable(completable.subscribe(onComplete, onError))
    }
}
