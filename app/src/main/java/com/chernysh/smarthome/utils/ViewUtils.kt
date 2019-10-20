package com.chernysh.smarthome.utils

import android.content.Context
import android.content.res.Resources

/**
 * Created by Andrii Chernysh on 4/14/19
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
fun Int.dpToPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()

fun Int.spToPx(): Int = (this * Resources.getSystem().displayMetrics.scaledDensity).toInt()