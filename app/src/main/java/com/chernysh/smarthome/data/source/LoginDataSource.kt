package com.chernysh.smarthome.data.source

import com.chernysh.smarthome.data.model.UserDto
import io.reactivex.Maybe
import io.reactivex.Observable

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
interface LoginDataSource {
  fun authUser(pin: String): Observable<UserDto>
}