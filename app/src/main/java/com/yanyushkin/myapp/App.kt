package com.yanyushkin.myapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.yanyushkin.myapp.di.AppComponent
import com.yanyushkin.myapp.di.DaggerAppComponent
import com.yanyushkin.myapp.di.FirebaseModule
import com.yanyushkin.myapp.di.PresentersModule

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initDayNightTheme()
        initComponent()
    }

    private fun initDayNightTheme(): Unit = AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    private fun initComponent() {
        component = DaggerAppComponent.builder()
            .firebaseModule(FirebaseModule())
            .presentersModule(PresentersModule())
            .build()
    }
}