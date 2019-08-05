package com.yanyushkin.myapp.arch

interface LoginContract {

    interface View {

        fun showProgress()

        fun onLoginSuccessful()

        fun onLoginError()

        fun onError(message: String)

        fun onEmailNotVerified()

        fun onFillingFieldsError()

        fun hideKeyBoard()

        fun setVisibleShowPassBtn()

        fun setInvisibleShowPassBtn()

        fun setVisiblePassword()

        fun setInvisiblePassword()

        fun onEmptyEmail()

        fun showMessageForRecoverPassword()
    }

    interface Presenter {

        fun attach(view: View)

        fun logIn(email: String, password: String)

        fun recoverPassword(email: String)

        fun setVisibilityOfShowPassBtn(hasFocus: Boolean)

        fun setVisibilityOfPass(isVisiblePassword: Boolean)
    }
}