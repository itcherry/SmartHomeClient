package com.chernysh.smarthome.data.repository

import com.chernysh.smarthome.data.network.source.LoginApiDataSource
import com.chernysh.smarthome.data.source.DataPolicy
import com.chernysh.smarthome.domain.repository.LoginRepository
import io.reactivex.Maybe
import java.lang.UnsupportedOperationException
import javax.inject.Inject

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class LoginRepositoryImpl @Inject constructor(
  private val loginApiDataSource: LoginApiDataSource
) : LoginRepository {
  override fun authUser(pinCode: String, dataPolicy: DataPolicy): Maybe<Any> {
    return when (dataPolicy) {
      DataPolicy.API -> loginApiDataSource.authUser(pinCode)
      else -> throw UnsupportedOperationException()
    }
  }

  override fun savePinCode(pinCode: String) {
    throw UnsupportedOperationException()
  }

}