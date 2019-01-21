package com.chernysh.smarthome.domain.model

/**
 * ViewState from MVI that describes most of states in the app (rozetka, lights, etc.)
 *
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 *         developed by <u>Transcendensoft</u>
 *         especially for Zhk Dinastija
 */
sealed class BooleanViewState {
    object LoadingState : BooleanViewState()
    data class DataState(val data: Boolean) : BooleanViewState()
    data class ErrorState(val error: Throwable) : BooleanViewState()
    object ConnectivityErrorState : BooleanViewState()
}