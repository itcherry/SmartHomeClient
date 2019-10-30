package com.chernysh.smarthome.domain.interactor.firebase

import com.chernysh.smarthome.domain.repository.FirebaseIdRepository
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class FirebaseTokenInteractor @Inject constructor(
    private val firebaseIdRepository: FirebaseIdRepository,
    private val schedulersTransformer: ObservableTransformer<Any, Any>
) {
    fun bindFirebaseId(token: String) {
        firebaseIdRepository.bindFirebaseToken()
    }
}