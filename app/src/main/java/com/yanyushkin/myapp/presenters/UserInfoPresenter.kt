package com.yanyushkin.myapp.presenters

import com.yanyushkin.myapp.Firebase
import com.yanyushkin.myapp.arch.UserInfoContract

class UserInfoPresenter : UserInfoContract.Presenter {

    private lateinit var userInfoView: UserInfoContract.View

    /**
     * binding to view
     */
    override fun attach(view: UserInfoContract.View) {
        this.userInfoView = view
    }

    override fun logout() {
        Firebase.mAuth.signOut()

        userInfoView.showSettingsFragment()
    }
}