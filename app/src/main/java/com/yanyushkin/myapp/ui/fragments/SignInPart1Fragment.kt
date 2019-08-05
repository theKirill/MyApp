package com.yanyushkin.myapp.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yanyushkin.myapp.App
import com.yanyushkin.myapp.Firebase
import com.yanyushkin.myapp.R
import com.yanyushkin.myapp.arch.SignInPart1Contract
import com.yanyushkin.myapp.extensions.*
import com.yanyushkin.myapp.presenters.SignInPart1Presenter
import com.yanyushkin.myapp.toast
import kotlinx.android.synthetic.main.fragment_signin.*
import javax.inject.Inject

/**
 * Фрагмент для ввода почты/пароля (регистрация)
 */
class SignInPart1Fragment : Fragment(), SignInPart1Contract.View {

    private object Holder {
        val INSTANCE = SignInPart1Fragment()
    }

    companion object {
        private const val ROTATION_KEY = "rotate"
        val instance: SignInPart1Fragment by lazy { Holder.INSTANCE }
    }

    @Inject
    lateinit var signInPresenter: SignInPart1Presenter
    private var isVisiblePassword = false
    private var rotate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

        doAfterRotate(savedInstanceState)

        App.component.injectsSignInPart1Fragment(this)
        signInPresenter.attach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val views = arrayListOf<View>(sign_email_et, sign_password_et, next_btn)
        if (!rotate)
            initAnimationForViews(views)

        initViewsOptions()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_signin, null)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        rotate = true
        outState.putBoolean(ROTATION_KEY, rotate)
    }

    override fun onSignInSuccessful() {
        android.os.Handler().postDelayed({
            Navigation.findNavController(activity as Activity, R.id.sign_nav_host_fragment)
                .navigate(R.id.action_signInPart1Fragment_to_signInPart2Fragment)
        }, 1000)
    }

    override fun onSignInError() {
        next_btn.revertAnimation {
            next_btn.background = resources.getDrawable(R.drawable.rounded_view, activity!!.theme)
        }

        toast(activity as Context, getString(R.string.error_sign_message))
    }

    override fun onError(): Unit = toast(activity as Context, getString(R.string.error_message))

    override fun onFillingFieldsError(): Unit = toast(activity as Context, getString(R.string.warning_login_message))

    override fun hideKeyBoard() {
        sign_main_layout.requestFocus()
        hideKeyboard(activity, next_btn)
    }

    override fun onValidEmail() {
        sign_email_done_iv.show()
        sign_email_done_iv.setImageResource(R.drawable.ic_done)
        sign_email_et.background = resources.getDrawable(R.drawable.ok_rounded_view, activity!!.theme)
    }

    override fun onInvalidEmail() {
        sign_email_done_iv.show()
        sign_email_done_iv.setImageResource(R.drawable.ic_error)
        sign_email_et.background =
            resources.getDrawable(R.drawable.error_rounded_view, activity!!.theme)
    }

    override fun onEmptyEmail() {
        sign_email_done_iv.hide()
        sign_email_et.background = resources.getDrawable(R.drawable.rounded_view, activity!!.theme)
    }

    override fun onValidPassword() {
        sign_password_et.background = resources.getDrawable(R.drawable.ok_rounded_view, activity!!.theme)
    }

    override fun onInvalidPassword() {
        sign_password_et.background =
            resources.getDrawable(R.drawable.error_rounded_view, activity!!.theme)
    }

    override fun onEmptyPassword() {
        sign_password_et.background = resources.getDrawable(R.drawable.rounded_view, activity!!.theme)
    }

    override fun verifyEmail(): Unit = toast(activity as Context, getString(R.string.email_verify_message))

    override fun setVisibleShowPassBtn() {
        sign_show_password_btn.show()
        sign_password_done_iv.hide()
    }

    override fun setInvisibleShowPassBtn() {
        sign_show_password_btn.hide()
    }

    override fun showValidityOfPassword() {
        sign_password_done_iv.show()
        sign_password_done_iv.setImageResource(R.drawable.ic_done)
    }

    override fun showInvalidityOfPassword() {
        sign_password_done_iv.show()
        sign_password_done_iv.setImageResource(R.drawable.ic_error)
        toast(
            activity as Context,
            resources.getString(R.string.info_password_message)
        )
    }

    override fun setVisiblePassword() {
        sign_password_et.showPassword()
        isVisiblePassword = true
    }

    override fun setInvisiblePassword() {
        sign_password_et.hidePassword()
        isVisiblePassword = false
    }

    override fun showProgress(): Unit = next_btn.startAnimation()

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

    private fun initViewsOptions() {
        initClickListenerForNextButton()
        initTextChangeListenerForEmailET()
        initTextChangeListenerForPassET()
        initFocusChangeListenerForPassET()
        initClickListenerForShowPassButton()
    }

    private fun initClickListenerForNextButton() {
        next_btn.setOnClickListener {
            signInPresenter.signIn(sign_email_et.text.toString(), sign_password_et.text.toString())
        }
    }

    private fun initTextChangeListenerForEmailET() {
        sign_email_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = sign_email_et.text.toString()

                signInPresenter.checkEmail(email)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initTextChangeListenerForPassET() {
        sign_password_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = sign_password_et.text.toString()

                signInPresenter.checkPassword(password)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initFocusChangeListenerForPassET() {
        sign_password_et.setOnFocusChangeListener { v, hasFocus ->
            val password = sign_password_et.text.toString()

            signInPresenter.setVisibilityOfShowPassBtn(hasFocus, password)
        }
    }

    private fun initClickListenerForShowPassButton() {
        sign_show_password_btn.setOnClickListener {
            signInPresenter.setVisibilityOfPass(isVisiblePassword)
        }
    }
}