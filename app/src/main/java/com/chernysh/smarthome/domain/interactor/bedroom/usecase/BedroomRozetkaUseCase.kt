package com.chernysh.smarthome.domain.interactor.bedroom.usecase

import com.chernysh.smarthome.domain.Method
import com.chernysh.smarthome.domain.SingleUseCase
import com.chernysh.smarthome.domain.repository.BedroomRepository
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Use case to manipulate rozetka state withing bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BedroomRozetkaUseCase @Inject constructor(singleTransformer: SingleTransformer<Any, Any>,
                                                compositeDisposable: CompositeDisposable,
                                                private val rozetkaRepository: BedroomRepository) :
    SingleUseCase<Boolean, BedroomRozetkaUseCase.Data>(singleTransformer, compositeDisposable) {

    override fun buildUseCaseSingle(params: Data): Single<Boolean> =
        when (params.method) {
            Method.GET -> rozetkaRepository.getRozetkaState()
            Method.SET -> rozetkaRepository.setRozetkaState(params.value).toSingle().map { params.value }
        }

    data class Data(val method: Method, val value: Boolean = false)
}