package com.yanyushkin.myapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yanyushkin.myapp.extensions.*
import com.yanyushkin.myapp.presenters.LoginPresenter
import com.yanyushkin.myapp.views.LoginView
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

/**
 * Фрагмент для ввода почты/пароля, входа в приложение
 */
class LoginFragment : Fragment(), LoginView {

    private object Holder {
        val INSTANCE = LoginFragment()
    }

    companion object {
        private const val SIGN_IN_CODE = 9001
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

    override fun onLoginSuccessful() {
        /*login_btn.doneLoadingAnimation(
            R.color.colorCompleteProgress,
            getBitmap(activity as Context, R.drawable.ic_complete)
        )*/

        Navigation.findNavController(activity as Activity, R.id.login_nav_host_fragment)
            .navigate(R.id.action_loginFragment_to_mainActivity)

        toast(activity as Context, getString(R.string.welcome_message))
    }

    override fun onLoginError() {
        /*login_btn.doneLoadingAnimation(
            R.color.colorCompleteProgress,
            getBitmap(activity as Context, R.drawable.ic_error_black)
        )*/

        toast(activity as Context, getString(R.string.error_login_message))
    }

    override fun showProgress(): Unit = login_btn.startAnimation()

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

    private fun initViewsOptions() {
        initClickListenerForLoginButtons()
        initFocusChangeListenerForPassET()
        initClickListenerForShowPassButton()
    }

    private fun initClickListenerForLoginButtons() {
        login_btn.setOnClickListener {
            hideKeyboard(activity, login_btn)
            login_main_layout.requestFocus()

            val email = email_et.text.toString()
            val password = password_et.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty())
                loginPresenter.logIn(email, password)
            else
                toast(activity as Context, getString(R.string.warning_login_message))
        }
    }

    private fun initFocusChangeListenerForPassET() {
        password_et.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                show_password_btn.show()
            else
                show_password_btn.hide()
        }
    }

    private fun initClickListenerForShowPassButton() {
        show_password_btn.setOnClickListener {
            if (!isVisiblePassword) {
                password_et.showPassword()
                isVisiblePassword = true
            } else {
                password_et.hidePassword()
                isVisiblePassword = false
            }
        }
    }
}