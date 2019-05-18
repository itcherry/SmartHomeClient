package com.chernysh.smarthome.data.source

import io.reactivex.Maybe
import io.reactivex.Observable

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
interface LoginDataSource {
  fun authUser(pin: String): Observable<Any>
  fun saveTokenToProtectedStorage(token: String)
}