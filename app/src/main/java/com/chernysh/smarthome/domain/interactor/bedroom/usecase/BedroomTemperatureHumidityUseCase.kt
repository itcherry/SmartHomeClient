package com.chernysh.smarthome.domain.interactor.bedroom.usecase

import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.ObservableUseCase
import com.chernysh.smarthome.domain.model.TemperatureHumidityData
import com.chernysh.smarthome.domain.repository.TemperatureHumidityRepository
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Use case to getting temperature and humidity withing bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BedroomTemperatureHumidityUseCase @Inject constructor(observableTransformer: ObservableTransformer<Any, Any>,
                                                            compositeDisposable: CompositeDisposable,
                                                            private val temperatureHumidityRepository: TemperatureHumidityRepository):
    ObservableUseCase<TemperatureHumidityData, Unit>(observableTransformer, compositeDisposable){

    override fun buildUseCaseObservable(params: Unit) = temperatureHumidityRepository
        .temperatureHumidityBedroomObservable(DataPolicy.SOCKET)
}