package com.yanyushkin.myapp.di

import com.yanyushkin.myapp.presenters.*
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

    @Singleton
    @Provides
    fun provideAccountPresenter(): AccountPresenter = AccountPresenter()

    @Singleton
    @Provides
    fun provideUserInfoPresenter(): UserInfoPresenter = UserInfoPresenter()

    @Singleton
    @Provides
    fun provideMainActivityPresenter(): MainActivityPresenter = MainActivityPresenter()
}