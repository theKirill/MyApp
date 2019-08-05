package com.yanyushkin.myapp.presenters

import com.yanyushkin.myapp.Firebase
import com.yanyushkin.myapp.arch.LoginContract

class LoginPresenter : LoginContract.Presenter {

    private lateinit var loginView: LoginContract.View

    /**
     * binding to view
     */
    override fun attach(view: LoginContract.View) {
        this.loginView = view
    }

    override fun logIn(email: String, password: String) {
        loginView.hideKeyBoard()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            loginView.showProgress()

            Firebase.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    if (Firebase.mAuth.currentUser!!.isEmailVerified)
                        loginView.onLoginSuccessful()
                    else
                        loginView.onEmailNotVerified()
                } else {
                    loginView.onLoginError()
                }
            }
        } else {
            loginView.onFillingFieldsError()
        }
    }

    override fun recoverPassword(email: String) {
        if (email.isNotEmpty())
            Firebase.mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful)
                    loginView.showMessageForRecoverPassword()
                else
                    loginView.onError(it.exception!!.message.toString())
            }
        else
            loginView.onEmptyEmail()
    }

    override fun setVisibilityOfShowPassBtn(hasFocus: Boolean) {
        if (hasFocus)
            loginView.setVisibleShowPassBtn()
        else
            loginView.setInvisibleShowPassBtn()
    }

    override fun setVisibilityOfPass(isVisiblePassword: Boolean) {
        if (!isVisiblePassword)
            loginView.setVisiblePassword()
        else
            loginView.setInvisiblePassword()
    }
}