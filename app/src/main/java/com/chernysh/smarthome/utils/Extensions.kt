package com.chernysh.smarthome.utils

import android.app.Activity
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Andrii Chernysh on 2020-01-15
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
inline fun <reified T : Activity> Activity.openActivity(initializer: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java).apply { initializer() }
    startActivity(intent)
}

fun Int.toHourAndMinute(): String = "${this / 60}:${this % 60}"