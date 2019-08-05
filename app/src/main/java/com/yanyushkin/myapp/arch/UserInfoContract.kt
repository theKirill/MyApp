package com.yanyushkin.myapp.arch

interface UserInfoContract {

    interface View {

        fun showSettingsFragment()
    }

    interface Presenter {

        fun attach(view: View)

        fun logout()
    }
}