package com.chernysh.smarthome.data.prefs

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class SmartHomePreferences(private val context: Context) {
    private val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(PREF_NAME, 0)
    }

    private fun getEditor(): SharedPreferences.Editor = preferences.edit()

    fun setFirebaseToken(token: String) {
        getEditor().putString(FIREBASE_TOKEN, token).apply()
    }

    fun getFirebaseToken(): String = preferences.getString(FIREBASE_TOKEN, "") ?: ""

    fun setFirebaseTokenBinded(firebaseTokenBinded: Boolean) {
        getEditor().putBoolean(FIREBASE_TOKEN_BINDED, firebaseTokenBinded).apply()
    }

    fun getFirebaseTokenBinded(): Boolean = preferences.getBoolean(FIREBASE_TOKEN_BINDED, false)

    companion object {
        private const val PREF_NAME = "DynastySmartHome"
        private const val FIREBASE_TOKEN = "firebaseToken"
        private const val FIREBASE_TOKEN_BINDED = "firebaseTokenBinded"
        private const val USER_ID = "userId"
    }
}