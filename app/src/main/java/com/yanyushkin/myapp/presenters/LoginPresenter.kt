package com.yanyushkin.myapp.presenters

import com.google.firebase.auth.FirebaseAuth
import com.yanyushkin.myapp.views.LoginView

class LoginPresenter : Presenter {

    private val mAuth: FirebaseAuth
    private lateinit var loginView: LoginView

    init {
        mAuth = FirebaseAuth.getInstance()
    }

    /**
     * binding to view
     */
    override fun attach(loginView: LoginView) {
        this.loginView = loginView
    }

    fun logIn(email: String, password: String) {
        showProgress()

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful)
                loginView.onLogInSuccessful()
            else
                loginView.onLogInError()
        }

        /*fun createUser(email: String, password: String) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        login_btn.doneLoadingAnimation(
                            R.color.colorCompleteProgress,
                            getBitmap(activity as Context)
                        )
                    else
                        Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                }
        }*/
    }

    private fun showProgress(): Unit = loginView.showProgress()
}