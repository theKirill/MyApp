package com.yanyushkin.myapp.presenters

import com.yanyushkin.myapp.views.LoginView
import com.yanyushkin.myapp.views.View

interface Presenter {

    fun attach(view: View)
}