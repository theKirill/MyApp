package com.yanyushkin.myapp.presenters

import com.yanyushkin.myapp.views.LoginView

interface Presenter {

    fun attach(loginView: LoginView)
}