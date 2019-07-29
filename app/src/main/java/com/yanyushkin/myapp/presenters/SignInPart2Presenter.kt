package com.yanyushkin.myapp.presenters

import com.google.firebase.auth.FirebaseAuth
import com.yanyushkin.myapp.App
import com.yanyushkin.myapp.views.SignInPart1View
import com.yanyushkin.myapp.views.SignInPart2View
import com.yanyushkin.myapp.views.View
import javax.inject.Inject

class SignInPart2Presenter {

    @Inject
    lateinit var mAuth: FirebaseAuth
    private lateinit var signInView: SignInPart2View

    init {
        App.component.injectsSignInPart2Presenter(this)
    }

    /**
     * binding to view
     */
    fun attach(view: View) {
        this.signInView = view as SignInPart2View
    }

    fun signIn(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful)
                signInView.onSignInSuccessful()
            else
                signInView.onSignInError()
        }
    }
}