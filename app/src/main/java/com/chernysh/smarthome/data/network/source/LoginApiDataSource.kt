package com.chernysh.smarthome.data.network.source

import com.chernysh.smarthome.data.model.UserDto
import com.chernysh.smarthome.data.source.LoginDataSource
import com.chernysh.smarthome.data.source.UserDataSource
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class LoginApiDataSource @Inject constructor() : ApiDataSource(), LoginDataSource {

    override fun authUser(pin: String): Observable<UserDto> = service.authUser(UserDto(LOGIN, pin, ""))

    companion object {
        private const val LOGIN = "admin"
    }

}