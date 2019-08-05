package com.yanyushkin.myapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.yanyushkin.myapp.di.AppComponent
import com.yanyushkin.myapp.di.DaggerAppComponent
import com.yanyushkin.myapp.di.PresentersModule
import com.yanyushkin.myapp.extensions.getAutoDayNight
import com.yanyushkin.myapp.extensions.getPreferences

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initDayNightTheme()
        initComponent()
    }

    private fun initDayNightTheme() {
        if (applicationContext.getPreferences().getAutoDayNight())
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun initComponent() {
        component = DaggerAppComponent.builder()
            .presentersModule(PresentersModule())
            .build()
    }
}