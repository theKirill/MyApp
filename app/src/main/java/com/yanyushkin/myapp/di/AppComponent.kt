package com.yanyushkin.myapp.di

import com.yanyushkin.myapp.ui.activities.MainActivity
import com.yanyushkin.myapp.ui.fragments.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PresentersModule::class])
interface AppComponent {

    fun injectsLoginFragment(loginFragment: LoginFragment)

    fun injectsSignInPart1Fragment(signInPart1Fragment: SignInPart1Fragment)

    fun injectsSignInPart2Fragment(signInPart2Fragment: SignInPart2Fragment)

    fun injectsAccountFragment(accountFragment: AccountFragment)

    fun injectsUserInfoFragment(userInfoFragment: UserInfoFragment)

    fun injectsMainActivity(mainActivity: MainActivity)
}