package com.yanyushkin.myapp.presenters

import com.google.firebase.auth.FirebaseAuth
import com.yanyushkin.myapp.App
import com.yanyushkin.myapp.views.SignInPart1View
import javax.inject.Inject

class SignInPart1Presenter {

    @Inject
    lateinit var mAuth: FirebaseAuth
    private lateinit var signInView: SignInPart1View

    init {
       App.component.injectsSignInPart1Presenter(this)
    }

    /**
     * binding to view
     */
    fun attach(signInViewPart1: SignInPart1View) {
        this.signInView = signInViewPart1
    }

    fun signInNext(): Unit = signInView.showNextPage()
}