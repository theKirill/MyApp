package com.yanyushkin.myapp.di

import com.yanyushkin.myapp.LoginFragment
import com.yanyushkin.myapp.presenters.LoginPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FirebaseModule::class, PresentersModule::class])
interface AppComponent {

    fun injectsLoginPresenter(loginPresenter: LoginPresenter)

    fun injectsLoginFragment(loginFragment: LoginFragment)
}