package com.yanyushkin.myapp.presenters

import com.yanyushkin.myapp.views.SignInPart1View

class SignInPart1Presenter {

    private lateinit var signInView: SignInPart1View

    /**
     * binding to view
     */
    fun attach(signInViewPart1: SignInPart1View) {
        this.signInView = signInViewPart1
    }

    fun signInNext(): Unit = signInView.showNextPage()
}