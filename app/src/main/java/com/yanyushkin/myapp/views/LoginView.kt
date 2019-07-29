package com.yanyushkin.myapp.views

interface LoginView : View {

    fun onLoginSuccessful()

    fun onLoginError()
}