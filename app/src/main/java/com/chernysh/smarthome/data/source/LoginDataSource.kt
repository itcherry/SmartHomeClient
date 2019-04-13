package com.chernysh.smarthome.data.source

import io.reactivex.Maybe

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
interface LoginDataSource {
  fun authUser(pin: String): Maybe<Any>
  fun savePinToProtectedStorage()
}