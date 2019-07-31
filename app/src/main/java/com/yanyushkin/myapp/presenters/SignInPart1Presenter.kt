package com.yanyushkin.myapp.presenters

import com.yanyushkin.myapp.Firebase
import com.yanyushkin.myapp.views.SignInView
import com.yanyushkin.myapp.views.View

class SignInPart1Presenter : Presenter {

    private lateinit var signInView: SignInView

    /**
     * binding to view
     */
    override fun attach(view: View) {
        this.signInView = view as SignInView
    }

    fun signIn(email: String, password: String) {
        signInView.showProgress()

        Firebase.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful)
                signInView.onSignInSuccessful()
            else
                signInView.onSignInError()
        }
    }
}