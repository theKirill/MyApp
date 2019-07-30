package com.yanyushkin.myapp.presenters

import com.google.firebase.auth.FirebaseAuth
import com.yanyushkin.myapp.App
import com.yanyushkin.myapp.Firebase
import com.yanyushkin.myapp.views.LoginView
import com.yanyushkin.myapp.views.View
import javax.inject.Inject

class LoginPresenter : Presenter {

    private lateinit var loginView: LoginView

    /**
     * binding to view
     */
    override fun attach(view: View) {
        this.loginView = view as LoginView
    }

    fun logIn(email: String, password: String) {
        showProgress()

        Firebase.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful)
                loginView.onLoginSuccessful()
            else
                loginView.onLoginError()
        }
    }

    private fun showProgress(): Unit = loginView.showProgress()
}