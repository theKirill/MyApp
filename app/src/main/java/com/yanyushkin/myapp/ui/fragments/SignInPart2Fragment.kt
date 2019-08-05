package com.yanyushkin.myapp.ui.fragments

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yanyushkin.myapp.App
import com.yanyushkin.myapp.R
import com.yanyushkin.myapp.arch.SignInPart2Contract
import com.yanyushkin.myapp.extensions.*
import com.yanyushkin.myapp.getBitmap
import com.yanyushkin.myapp.presenters.SignInPart2Presenter
import com.yanyushkin.myapp.toast
import kotlinx.android.synthetic.main.fragment_signin2.*
import javax.inject.Inject

/**
 * Фрагмент для ввода имени+пола, загрузки фото профиля (регистрация)
 */
class SignInPart2Fragment : Fragment(), SignInPart2Contract.View {

    private object Holder {
        val INSTANCE = SignInPart2Fragment()
    }

    companion object {
        private const val ROTATION_KEY = "rotate"
        private const val GALLERY_CODE_REQUEST = 1
        val instance: SignInPart2Fragment by lazy { Holder.INSTANCE }
    }

    @Inject
    lateinit var signInPresenter: SignInPart2Presenter
    private var rotate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

        doAfterRotate(savedInstanceState)

        App.component.injectsSignInPart2Fragment(this)
        signInPresenter.attach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val views = arrayListOf<View>(sign_photo_iv, sign_name_et, sign_btn)
        if (!rotate)
            initAnimationForViews(views)

        initClickListenersForViews()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_signin2, null)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        rotate = true
        outState.putBoolean(ROTATION_KEY, rotate)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onAddInfoSuccessful() {
        sign_btn.doneLoadingAnimation(
            resources.getColor(R.color.colorCompleteProgress, activity!!.theme),
            getBitmap(activity as Context, R.drawable.complete)
        )

        android.os.Handler().postDelayed({
            Navigation.findNavController(activity as Activity, R.id.sign_nav_host_fragment)
                .navigate(R.id.action_signInPart2Fragment_to_mainActivity2)

            toast(
                activity as Context,
                getString(R.string.welcome_after_sign_message)
            )
        }, 1000)
    }

    override fun onAddInfoError() {

    }

    override fun hideKeyBoard() {
        sign_part2_layout.requestFocus()
        hideKeyboard(activity, sign_btn)
    }

    override fun openGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"

        startActivityForResult(
            photoPickerIntent,
            GALLERY_CODE_REQUEST
        )
    }

    override fun setPhotoToIV(data: Intent?) {
        val selectedPhoto = data!!.data

        sign_photo_iv.setImageURI(selectedPhoto)
    }

    override fun showErrorWhenSettingPhoto(): Unit =
        toast(activity as Context, getString(R.string.error_message))

    override fun showProgress(): Unit = sign_btn.startAnimation()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        signInPresenter.setPhoto(requestCode, resultCode, data)
    }

    private fun doAfterRotate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            savedInstanceState.apply {
                if (containsKey(ROTATION_KEY) && getBoolean(
                        ROTATION_KEY
                    )
                ) {
                    rotate = true
                    clear()
                }
            }
        }
    }

    private fun initAnimationForViews(views: ArrayList<View>) {
        var startOffset: Long = 0

        views.forEach {
            it.animate(activity as Context, startOffset)
            startOffset += 250
        }

        rotate = false
    }

    private fun initClickListenersForViews() {
        sign_photo_iv.setOnClickListener {
            signInPresenter.choosePhoto()
        }

        sign_btn.setOnClickListener {
            signInPresenter.addAdditionalInfoAboutUser()
        }
    }
}