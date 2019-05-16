package com.chernysh.smarthome.data.network.source

import com.chernysh.smarthome.data.source.LoginDataSource
import com.chernysh.smarthome.data.source.UserDataSource
import io.reactivex.Maybe
import java.lang.UnsupportedOperationException
import javax.inject.Inject

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class LoginApiDataSource @Inject constructor(
    private val userDataSource: UserDataSource
) : ApiDataSource(), LoginDataSource {

    override fun authUser(pin: String): Maybe<Any> = service.authUser(PIN_CODE_KEY to pin)

    override fun saveTokenToProtectedStorage(token: String) {
        userDataSource.setToken(token)
    }

    companion object {
        private const val PIN_CODE_KEY = "pinCode"
    }

}