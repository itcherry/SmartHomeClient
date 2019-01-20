package com.chernysh.smarthome.domain.interactor.bedroom.usecase

import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.Method
import com.chernysh.smarthome.domain.ObservableUseCase
import com.chernysh.smarthome.domain.SingleUseCase
import com.chernysh.smarthome.domain.model.TemperatureHumidityData
import com.chernysh.smarthome.domain.repository.BedroomRepository
import com.chernysh.smarthome.domain.repository.TemperatureHumidityRepository
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Use case to manipulate lights state withing bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BedroomLightsUseCase @Inject constructor(singleTransformer: SingleTransformer<Any, Any>,
                                               compositeDisposable: CompositeDisposable,
                                               private val bedroomRepository: BedroomRepository) :
    SingleUseCase<Boolean, BedroomLightsUseCase.Data>(singleTransformer, compositeDisposable) {

    override fun buildUseCaseSingle(params: Data): Single<Boolean> =
        when (params.method) {
            Method.GET -> bedroomRepository.getLightState()
            Method.SET -> bedroomRepository.setLightState(params.value).toSingle().map { params.value }
        }

    data class Data(val method: Method, val value: Boolean = false)
}