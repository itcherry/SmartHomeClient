package com.chernysh.smarthome.domain.interactor.firebase

import com.chernysh.smarthome.data.prefs.SmartHomePreferences
import com.chernysh.smarthome.domain.model.LoginViewState
import com.chernysh.smarthome.domain.repository.FirebaseIdRepository
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class FirebaseTokenInteractor @Inject constructor(
    private val firebaseIdRepository: FirebaseIdRepository,
    private val preferences: SmartHomePreferences,
    private val schedulersTransformer: ObservableTransformer<Any, Any>
) {
    fun bindFirebaseId(token: String) =
        firebaseIdRepository.bindFirebaseToken(token)
            .toObservable()
            .doOnComplete {
                preferences.setFirebaseToken(token)
                preferences.setFirebaseTokenBinded(true)
            }
            .compose(schedulersTransformer)
}