package com.yanyushkin.myapp.presenters

import com.yanyushkin.myapp.Firebase
import com.yanyushkin.myapp.arch.SignInPart1Contract

class SignInPart1Presenter : SignInPart1Contract.Presenter {

    private lateinit var signInView: SignInPart1Contract.View

    /**
     * binding to view
     */
    override fun attach(view: SignInPart1Contract.View) {
        this.signInView = view
    }

    override fun signIn(email: String, password: String) {
        signInView.hideKeyBoard()

        if (isValidEmail(email) && isValidPassword(password)) {
            signInView.showProgress()

            Firebase.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    verifyEmail()
                    signInView.onSignInSuccessful()
                } else
                    signInView.onSignInError()
            }
        } else {
            signInView.onFillingFieldsError()
        }
    }

    override fun setVisibilityOfPass(isVisiblePassword: Boolean) {
        if (isVisiblePassword)
            signInView.setVisiblePassword()
        else
            signInView.setInvisiblePassword()
    }

    override fun checkEmail(email: String) {
        if (isValidEmail(email)) {
            signInView.onValidEmail()
        } else {
            if (email.isNotEmpty())
                signInView.onInvalidEmail()
            else
                signInView.onEmptyEmail()
        }
    }

    override fun checkPassword(password: String) {
        if (isValidPassword(password)) {
            signInView.onValidPassword()
        } else {
            if (password.isNotEmpty())
                signInView.onInvalidPassword()
            else
                signInView.onEmptyPassword()
        }
    }

    override fun setVisibilityOfShowPassBtn(hasFocus: Boolean, password: String) {
        if (hasFocus) {
            signInView.setVisibleShowPassBtn()
        } else {
            signInView.setInvisibleShowPassBtn()
            setVisibilityOfValidPass(password)
        }
    }

    private fun setVisibilityOfValidPass(password: String) {
        if (password.isNotEmpty()) {
            if (isValidPassword(password))
                signInView.showValidityOfPassword()
            else
                signInView.showInvalidityOfPassword()
        }
    }

    private fun isValidEmail(email: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun verifyEmail() {
        Firebase.mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener {
            if (it.isSuccessful)
                signInView.verifyEmail()
            else
                signInView.onError()
        }
    }
}