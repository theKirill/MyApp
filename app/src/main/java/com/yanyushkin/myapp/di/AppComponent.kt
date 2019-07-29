package com.yanyushkin.myapp.di

import com.yanyushkin.myapp.LoginFragment
import com.yanyushkin.myapp.SignInPart1Fragment
import com.yanyushkin.myapp.SignInPart2Fragment
import com.yanyushkin.myapp.presenters.LoginPresenter
import com.yanyushkin.myapp.presenters.SignInPart1Presenter
import com.yanyushkin.myapp.presenters.SignInPart2Presenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FirebaseModule::class, PresentersModule::class])
interface AppComponent {

    fun injectsLoginPresenter(loginPresenter: LoginPresenter)

    fun injectsLoginFragment(loginFragment: LoginFragment)

    fun injectsSignInPart1Presenter(signInPart1Presenter: SignInPart1Presenter)

    fun injectsSignInPart1Fragment(signInPart1Fragment: SignInPart1Fragment)

    fun injectsSignInPart2Presenter(signInPart2Presenter: SignInPart2Presenter)

    fun injectsSignInPart2Fragment(signInPart2Fragment: SignInPart2Fragment)
}