package com.yanyushkin.myapp.views

interface SignInView : View {

    fun onSignInSuccessful()

    fun onSignInError()
}