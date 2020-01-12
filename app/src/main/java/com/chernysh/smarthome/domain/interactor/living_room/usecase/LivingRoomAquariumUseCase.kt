package com.chernysh.smarthome.domain.interactor.living_room.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.model.LivingRoomPartialViewState
import com.chernysh.smarthome.domain.repository.LivingRoomRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case to manipulate aquarium state withing living room
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class LivingRoomAquariumUseCase @Inject constructor(private val livingRoomRepository: LivingRoomRepository) {
    fun processLivingRoomAquarium(params: Data): Observable<LivingRoomPartialViewState> =
        when (params.method) {
            Method.GET -> livingRoomRepository.getAquariumState()
            Method.SET -> livingRoomRepository.setAquariumState(params.value).switchIfEmpty(Single.just(params.value))
        }
            .toObservable()
            .map<BooleanViewState> { BooleanViewState.DataState(it) }
            .startWith(BooleanViewState.LoadingState)
            .onErrorReturn {
                if (it is NoConnectivityException) {
                    BooleanViewState.ConnectivityErrorState
                } else {
                    BooleanViewState.ErrorState(it)
                }
            }
            .map { LivingRoomPartialViewState.AquariumState(it) }

    data class Data(val method: Method, val value: Boolean = false)
}