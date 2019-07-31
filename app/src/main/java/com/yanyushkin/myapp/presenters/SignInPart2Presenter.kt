package com.yanyushkin.myapp.presenters

import com.yanyushkin.myapp.views.SignInView
import com.yanyushkin.myapp.views.View

class SignInPart2Presenter : Presenter {

    private lateinit var signInView: SignInView

    /**
     * binding to view
     */
    override fun attach(view: View) {
        this.signInView = view as SignInView
    }

    fun addAdditionalInfoAboutUser(){
        signInView.showProgress()

        signInView.onSignInSuccessful()
    }
}