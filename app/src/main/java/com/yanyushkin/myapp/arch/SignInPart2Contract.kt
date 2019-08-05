package com.yanyushkin.myapp.arch

import android.content.Intent

interface SignInPart2Contract {

    interface View {

        fun showProgress()

        fun onAddInfoSuccessful()

        fun onAddInfoError()

        fun hideKeyBoard()

        fun openGallery()

        fun setPhotoToIV(data: Intent?)

        fun showErrorWhenSettingPhoto()
    }

    interface Presenter {

        fun attach(view: View)

        fun addAdditionalInfoAboutUser()

        fun choosePhoto()

        fun setPhoto(requestCode: Int, resultCode: Int, data: Intent?)
    }
}