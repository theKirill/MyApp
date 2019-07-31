package com.yanyushkin.myapp

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yanyushkin.myapp.extensions.*
import com.yanyushkin.myapp.presenters.SignInPart2Presenter
import com.yanyushkin.myapp.views.SignInView
import kotlinx.android.synthetic.main.fragment_signin2.*
import javax.inject.Inject

/**
 * Фрагмент для ввода имени, загрузки фото профиля
 */
class SignInPart2Fragment : Fragment(), SignInView {

    private object Holder {
        val INSTANCE = SignInPart2Fragment()
    }

    companion object {
        val instance: SignInPart2Fragment by lazy { Holder.INSTANCE }
    }

    @Inject
    lateinit var signInPresenter: SignInPart2Presenter
    private val ROTATION_KEY = "rotate"
    private var rotate = false
    private val GALLERY_CODE_REQUEST = 1

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

        initClickListenerForOkButton()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_signin2, null)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        rotate = true
        outState.putBoolean(ROTATION_KEY, rotate)
    }

    override fun onSignInSuccessful() {
        Navigation.findNavController(activity as Activity, R.id.sign_nav_host_fragment)
            .navigate(R.id.action_signInPart2Fragment_to_mainActivity2)

        toast(activity as Context, getString(R.string.welcome_after_sign_message))
    }

    override fun onSignInError() {

    }

    override fun showProgress(): Unit = sign_btn.startAnimation()

    private fun doAfterRotate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            savedInstanceState.apply {
                if (containsKey(ROTATION_KEY) && getBoolean(ROTATION_KEY)) {
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

    private fun initClickListenerForOkButton() {
        sign_btn.setOnClickListener {
            sign_part2_layout.requestFocus()
            hideKeyboard(activity, sign_btn)

            signInPresenter.addAdditionalInfoAboutUser()
        }

        sign_photo_iv.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"

            startActivityForResult(photoPickerIntent, GALLERY_CODE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_CODE_REQUEST && resultCode == RESULT_OK) {
            val selectedPhoto = data!!.data

            sign_photo_iv.setImageURI(selectedPhoto)
        }
    }
}