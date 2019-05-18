package com.chernysh.smarthome.data.cache

import com.chernysh.smarthome.data.exception.NoSuchValueError
import com.chernysh.smarthome.data.source.UserDataSource
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

/**
 * Created by Andrii Chernysh on 2019-05-16
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class UserCacheDataSource @Inject constructor(): UserDataSource {
    private val tokenCache = AtomicReference<String>()

    override fun getToken(): String = tokenCache.get() ?: ""

    override fun setToken(token: String) {
        tokenCache.set(token)
    }
}