package com.yanyushkin.myapp.presenters

import com.google.firebase.auth.FirebaseAuth
import com.yanyushkin.myapp.App
import com.yanyushkin.myapp.views.LoginView
import com.yanyushkin.myapp.views.View
import javax.inject.Inject

class LoginPresenter : Presenter {

    @Inject
    lateinit var mAuth: FirebaseAuth
    private lateinit var loginView: LoginView

    init {
       App.component.injectsLoginPresenter(this)
    }

    /**
     * binding to view
     */
    override fun attach(view: View) {
        this.loginView = view as LoginView
    }

    fun logIn(email: String, password: String) {
        showProgress()

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful)
                loginView.onLoginSuccessful()
            else
                loginView.onLoginError()
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