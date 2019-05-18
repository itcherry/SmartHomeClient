package com.chernysh.smarthome.domain.interactor.living_room.usecase

import com.chernysh.smarthome.data.exception.NoConnectivityException
import com.chernysh.smarthome.domain.model.BooleanViewState
import com.chernysh.smarthome.domain.model.Method
import com.chernysh.smarthome.domain.model.RoomPartialWithoutLightsViewState
import com.chernysh.smarthome.domain.repository.LivingRoomRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use case to manipulate rozetka state withing living room
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
class LivingRoomRozetkaUseCase @Inject constructor(private val livingRoomRepository: LivingRoomRepository) {
    fun processLivingRoomRozetka(params: Data): Observable<RoomPartialWithoutLightsViewState> =
        when (params.method) {
            Method.GET -> livingRoomRepository.getRozetkaState()
            Method.SET -> livingRoomRepository.setRozetkaState(params.value).toSingle().map { params.value }
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
            .map { RoomPartialWithoutLightsViewState.RozetkaState(it) }

    data class Data(val method: Method, val value: Boolean = false)
}