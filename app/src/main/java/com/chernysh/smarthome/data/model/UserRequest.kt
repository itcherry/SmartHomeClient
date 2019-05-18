package com.chernysh.smarthome.data.model

import com.google.gson.annotations.SerializedName

data class UserRequest(@SerializedName("login") val login: String,
                       @SerializedName("password") val password: String)