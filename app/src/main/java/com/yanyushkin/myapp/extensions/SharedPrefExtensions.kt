package com.yanyushkin.myapp.extensions

import android.content.Context
import android.content.SharedPreferences

private const val APP_PREFERENCES_KEY = "settings"

/**
 * Для работы с SharedPref
 */
fun Context.getPreferences(): SharedPreferences = getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)

/**
 * Берём из настроек информацию об автоматическом включении темы дня/ночи
 */
fun SharedPreferences.getAutoDayNight(): Boolean = getBoolean("auto_daynight", false)