package com.yanyushkin.myapp.ui.fragments

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yanyushkin.myapp.App
import com.yanyushkin.myapp.R
import com.yanyushkin.myapp.arch.LoginContract
import com.yanyushkin.myapp.extensions.*
import com.yanyushkin.myapp.getBitmap
import com.yanyushkin.myapp.presenters.LoginPresenter
import com.yanyushkin.myapp.toast
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

/**
 * Фрагмент для ввода почты/пароля, входа в приложение
 */
class LoginFragment : Fragment(), LoginContract.View {

    private object Holder {
        val INSTANCE = LoginFragment()
    }

    companion object {
        private const val ROTATION_KEY = "rotate"
        val instance: LoginFragment by lazy { Holder.INSTANCE }
    }

    @Inject
    lateinit var loginPresenter: LoginPresenter
    private var isVisiblePassword = false
    private var rotate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

        doAfterRotate(savedInstanceState)

        App.component.injectsLoginFragment(this)
        loginPresenter.attach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val views = arrayListOf<View>(email_et, password_et, login_btn, forgot_password_btn)
        if (!rotate)
            initAnimationForViews(views)

        initViewsOptions()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_login, null)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        rotate = true
        outState.putBoolean(ROTATION_KEY, rotate)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onLoginSuccessful() {
        showOkButton()

        android.os.Handler().postDelayed({
            Navigation.findNavController(activity as Activity, R.id.login_nav_host_fragment)
                .navigate(R.id.action_loginFragment_to_mainActivity)

            toast(activity as Context, getString(R.string.welcome_message))
        }, 1000)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onLoginError() {
        showErrorButton()

        toast(activity as Context, getString(R.string.error_login_message))
    }

    override fun onError(message: String): Unit = toast(activity as Context, message)

    override fun onEmailNotVerified() {
        showErrorButton()

        toast(activity as Context, resources.getString(R.string.email_verify_warning_message))
    }

    override fun onFillingFieldsError(): Unit = toast(activity as Context, getString(R.string.warning_login_message))

    override fun hideKeyBoard() {
        hideKeyboard(activity, login_btn)
        login_main_layout.requestFocus()
    }

    override fun setVisibleShowPassBtn(): Unit = show_password_btn.show()

    override fun setInvisibleShowPassBtn(): Unit = show_password_btn.hide()

    override fun setVisiblePassword() {
        password_et.showPassword()
        isVisiblePassword = true
    }

    override fun setInvisiblePassword() {
        password_et.hidePassword()
        isVisiblePassword = false
    }

    override fun onEmptyEmail(): Unit = toast(activity as Context, getString(R.string.empty_email_warning_message))

    override fun showMessageForRecoverPassword(): Unit =
        toast(activity as Context, getString(R.string.recover_password_warning_message))

    override fun showProgress(): Unit = login_btn.startAnimation()

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
        initClickListenerForButtons()
        initFocusChangeListenerForPassET()
        initClickListenerForShowPassButton()
    }

    private fun initClickListenerForButtons() {
        login_btn.setOnClickListener {
            val email = email_et.text.toString()
            val password = password_et.text.toString()

            loginPresenter.logIn(email, password)
        }

        forgot_password_btn.setOnClickListener {
            val email = email_et.text.toString()

            loginPresenter.recoverPassword(email)
        }
    }

    private fun initFocusChangeListenerForPassET() {
        password_et.setOnFocusChangeListener { v, hasFocus ->
            loginPresenter.setVisibilityOfShowPassBtn(hasFocus)
        }
    }

    private fun initClickListenerForShowPassButton() {
        show_password_btn.setOnClickListener {
            loginPresenter.setVisibilityOfPass(isVisiblePassword)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun showOkButton() {
        login_btn.doneLoadingAnimation(
            resources.getColor(R.color.colorCompleteProgress, activity!!.theme),
            getBitmap(activity as Context, R.drawable.complete)
        )
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun showErrorButton() {
        login_btn.doneLoadingAnimation(
            resources.getColor(R.color.colorError, activity!!.theme),
            getBitmap(activity as Context, R.drawable.not_complete)
        )

        android.os.Handler().postDelayed({
            login_btn.revertAnimation {
                login_btn.background = resources.getDrawable(R.drawable.rounded_view, activity!!.theme)
            }
        }, 1000)
    }
}