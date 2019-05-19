package com.chernysh.smarthome.data.repository

import com.chernysh.smarthome.data.network.source.LoginApiDataSource
import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.data.source.UserDataSource
import com.chernysh.smarthome.domain.repository.LoginRepository
import com.chernysh.smarthome.utils.Notification
import io.reactivex.Maybe
import io.reactivex.Observable
import java.lang.UnsupportedOperationException
import javax.inject.Inject

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class LoginRepositoryImpl @Inject constructor(
    private val loginApiDataSource: LoginApiDataSource,
    private val userDataSource: UserDataSource
) : LoginRepository {
    override fun authUser(pinCode: String, dataPolicy: DataPolicy): Observable<Any> {
        return when (dataPolicy) {
            DataPolicy.API -> loginApiDataSource.authUser(pinCode)
                .doOnNext { userDataSource.setToken(it.securityToken ?: "") }
                .map { Notification.INSTANCE }

            else -> throw UnsupportedOperationException()
        }
    }
}