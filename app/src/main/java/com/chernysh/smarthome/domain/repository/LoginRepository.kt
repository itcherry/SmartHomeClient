package com.chernysh.smarthome.domain.repository

import com.chernysh.smarthome.data.source.DataPolicy
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
interface LoginRepository {
  fun authUser(pinCode: String, dataPolicy: DataPolicy): Maybe<Any>
  fun saveJwtToken(token: String)
}