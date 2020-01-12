package com.chernysh.smarthome.domain.interactor.living_room.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.LivingRoomPartialViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.model.RoomPartialViewState
import com.chernysh.smarthome.domain.repository.LivingRoomRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case to manipulate lights state withing living room
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class LivingRoomLightsUseCase @Inject constructor(private val livingRoomRepository: LivingRoomRepository) {
    fun processLivingRoomLights(params: Data): Observable<LivingRoomPartialViewState> =
        when (params.method) {
            Method.GET -> livingRoomRepository.getLightState()
            Method.SET -> livingRoomRepository.setLightState(params.value).switchIfEmpty(Single.just(params.value))
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
            .map { LivingRoomPartialViewState.LightsState(it) }


    data class Data(val method: Method, val value: Boolean = false)
}