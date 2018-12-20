package ua.andrii.chernysh.rxsockets.domain

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

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 *
 * By convention each UseCase implementation will return the result using a
 * [io.reactivex.observers.DisposableObserver] that will execute its
 * job in a background thread and will post the result in the UI thread.
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 */

abstract class UseCase(private val mCompositeDisposable: CompositeDisposable?) {
    /**
     * Dispose from current [CompositeDisposable].
     */
    fun dispose() {
        if (!mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    protected fun addDisposable(disposable: Disposable?) {
        if (disposable == null) {
            throw NullPointerException()
        }
        if (mCompositeDisposable == null) {
            throw NullPointerException(NULL_COMPOSITE_DISPOSABLE_MSG)
        }
        mCompositeDisposable.add(disposable)
    }

    companion object {
        val NULL_DISPOSABLE_OBSERVER_MSG = "Disposable observer is NULL." + " Check your Interactor execution within Presenter"
        val NULL_COMPOSITE_DISPOSABLE_MSG = "Composite disposable is null." + " Check Dagger 2 dependencies."
    }
}
