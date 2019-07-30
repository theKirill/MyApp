package com.yanyushkin.myapp.presenters

import com.yanyushkin.myapp.Firebase
import com.yanyushkin.myapp.views.SignInPart2View
import com.yanyushkin.myapp.views.View

class SignInPart2Presenter {

    private lateinit var signInView: SignInPart2View

    /**
     * binding to view
     */
    fun attach(view: View) {
        this.signInView = view as SignInPart2View
    }

    fun signIn(email: String, password: String) {
        Firebase.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful)
                signInView.onSignInSuccessful()
            else
                signInView.onSignInError()
        }
    }
}