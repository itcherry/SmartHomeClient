package com.chernysh.smarthome.domain

import io.reactivex.Maybe
import io.reactivex.MaybeTransformer
import io.reactivex.disposables.CompositeDisposable
import ua.andrii.chernysh.rxsockets.domain.UseCase

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 *
 * This use case executes some command with
 * [Maybe] boundaries
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
abstract class MaybeUseCase<T, PARAM>(private val mMaybeTransformer: MaybeTransformer<Any, Any>,
                                      mCompositeDisposable: CompositeDisposable) : UseCase(mCompositeDisposable) {

    /**
     * Builds an Observable which will be
     * used when executing the current [UseCase].
     */
    protected abstract fun buildUseCaseMaybe(params: PARAM): Maybe<T>

    /**
     * Executes the current use case.
     */
    fun execute(params: PARAM, onSuccess: (t: T) -> Unit = {},
                onError: (err: Throwable) -> Unit = {},
                onComplete: () -> Unit = {}) {
        val maybe = this.buildUseCaseMaybe(params)
            .compose(applySchedulers())

        addDisposable(maybe.subscribe(onSuccess, onError, onComplete))
    }

    private fun <S> applySchedulers(): MaybeTransformer<S, S> {
        return mMaybeTransformer as MaybeTransformer<S, S>
    }
}
