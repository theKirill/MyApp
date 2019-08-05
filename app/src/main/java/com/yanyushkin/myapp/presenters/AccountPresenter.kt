package com.yanyushkin.myapp.presenters

import com.google.firebase.auth.FirebaseUser
import com.yanyushkin.myapp.Firebase
import com.yanyushkin.myapp.arch.AccountContract

class AccountPresenter : AccountContract.Presenter {

    private lateinit var accountView: AccountContract.View

    /**
     * binding to view
     */
    override fun attach(view: AccountContract.View) {
        this.accountView = view
    }

    override fun checkUserAvailability(firebaseUser: FirebaseUser?) {
        firebaseUser?.let {
            accountView.showAccount(Firebase.mAuth.currentUser!!.email.toString())
        }
    }
}