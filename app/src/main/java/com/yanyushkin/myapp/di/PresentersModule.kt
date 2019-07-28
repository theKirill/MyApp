package com.yanyushkin.myapp.di

import com.yanyushkin.myapp.presenters.LoginPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentersModule {

    @Singleton
    @Provides
    fun provideLoginPresenter(): LoginPresenter = LoginPresenter()
}