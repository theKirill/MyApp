package com.yanyushkin.myapp.arch

interface MainActivityContract {

    interface View {

        fun showTab(id: Int)
    }

    interface Presenter {

        fun attach(view: View)

        fun showTab(id: Int)
    }
}