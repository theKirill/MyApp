package com.yanyushkin.myapp.arch

interface SignInPart1Contract {

    interface View {

        fun showProgress()

        fun onSignInSuccessful()

        fun onSignInError()

        fun onFillingFieldsError()

        fun hideKeyBoard()

        fun onValidEmail()

        fun onInvalidEmail()

        fun onEmptyEmail()

        fun onValidPassword()

        fun onInvalidPassword()

        fun onEmptyPassword()

        fun setVisibleShowPassBtn()

        fun setInvisibleShowPassBtn()

        fun showValidityOfPassword()

        fun showInvalidityOfPassword()

        fun setVisiblePassword()

        fun setInvisiblePassword()
    }

    interface Presenter {

        fun attach(view: View)

        fun signIn(email: String, password: String)

        fun setVisibilityOfPass(isVisiblePassword: Boolean)

        fun checkEmail(email: String)

        fun checkPassword(password: String)

        fun setVisibilityOfShowPassBtn(hasFocus: Boolean, password: String)
    }
}