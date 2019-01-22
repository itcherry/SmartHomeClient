package com.chernysh.smarthome.domain.interactor.bedroom.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.repository.BedroomRepository
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Use case to manipulate rozetka state withing bedroom
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class BedroomRozetkaUseCase @Inject constructor(private val bedroomRepository: BedroomRepository) {
    fun processBedroomRozetka(params: Data): Observable<BooleanViewState> =
        when (params.method) {
            Method.GET -> bedroomRepository.getRozetkaState()
            Method.SET -> bedroomRepository.setRozetkaState(params.value).toSingle().map { params.value }
        }
            .toObservable()
            .map<BooleanViewState> { BooleanViewState.DataState(it) }
            .startWith { BooleanViewState.LoadingState }
            .onErrorReturn {
                if (it is NoConnectivityException) {
                    BooleanViewState.ConnectivityErrorState
                } else {
                    BooleanViewState.ErrorState(it)
                }
            }

    data class Data(val method: Method, val value: Boolean = false)
}