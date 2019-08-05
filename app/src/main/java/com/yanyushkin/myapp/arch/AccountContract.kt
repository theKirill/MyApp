package com.yanyushkin.myapp.arch

import com.google.firebase.auth.FirebaseUser

interface AccountContract {

    interface View {

        fun showAccount(email: String)
    }

    interface Presenter {

        fun attach(view: View)

        fun checkUserAvailability(firebaseUser: FirebaseUser?)
    }
}