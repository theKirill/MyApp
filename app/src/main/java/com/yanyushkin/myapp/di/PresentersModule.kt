package com.yanyushkin.myapp.di

import com.yanyushkin.myapp.presenters.LoginPresenter
import com.yanyushkin.myapp.presenters.SignInPart1Presenter
import com.yanyushkin.myapp.presenters.SignInPart2Presenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentersModule {

    @Singleton
    @Provides
    fun provideLoginPresenter(): LoginPresenter = LoginPresenter()

    @Singleton
    @Provides
    fun provideSignInPart1Presenter(): SignInPart1Presenter = SignInPart1Presenter()

    @Singleton
    @Provides
    fun provideSignInPart2Presenter(): SignInPart2Presenter = SignInPart2Presenter()
}