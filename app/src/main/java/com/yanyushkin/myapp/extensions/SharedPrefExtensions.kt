package com.yanyushkin.myapp.extensions

import android.content.Context
import android.content.SharedPreferences

private const val APP_PREFERENCES_KEY = "settings"

/**
 * for work with shared preferences
 */
fun Context.getPreferences(): SharedPreferences = getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)

fun SharedPreferences.getAutoDayNight(): Boolean = getBoolean("auto_daynight", true)