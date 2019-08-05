package com.yanyushkin.myapp.presenters

import android.app.Activity
import android.content.Intent
import com.yanyushkin.myapp.arch.SignInPart2Contract

class SignInPart2Presenter : SignInPart2Contract.Presenter {

    companion object {
        private const val GALLERY_CODE_REQUEST = 1
    }

    private lateinit var signInView: SignInPart2Contract.View

    /**
     * binding to view
     */
    override fun attach(view: SignInPart2Contract.View) {
        this.signInView = view
    }

    override fun addAdditionalInfoAboutUser() {
        signInView.hideKeyBoard()
        signInView.showProgress()

        signInView.onAddInfoSuccessful()
    }

    override fun choosePhoto(): Unit = signInView.openGallery()

    override fun setPhoto(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GALLERY_CODE_REQUEST && resultCode == Activity.RESULT_OK)
            signInView.setPhotoToIV(data)
        else
            signInView.showErrorWhenSettingPhoto()
    }
}