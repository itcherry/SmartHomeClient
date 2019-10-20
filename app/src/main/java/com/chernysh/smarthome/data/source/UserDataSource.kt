package com.chernysh.smarthome.data.source

/**
 * Created by Andrii Chernysh on 2019-05-16
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
interface UserDataSource {
    fun getToken(): String?
    fun setToken(token: String)
}