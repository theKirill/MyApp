package com.yanyushkin.myapp.arch

interface LoginContract {

    interface View {

        fun showProgress()

        fun onLoginSuccessful()

        fun onLoginError()

        fun onEmailNotVerified()

        fun onFillingFieldsError()

        fun hideKeyBoard()

        fun setVisibleShowPassBtn()

        fun setInvisibleShowPassBtn()

        fun setVisiblePassword()

        fun setInvisiblePassword()
    }

    interface Presenter {

        fun attach(view: View)

        fun logIn(email: String, password: String)

        fun setVisibilityOfShowPassBtn(hasFocus: Boolean)

        fun setVisibilityOfPass(isVisiblePassword: Boolean)
    }
}