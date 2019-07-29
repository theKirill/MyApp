package com.yanyushkin.myapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
        val instance: LoginFragment by lazy { Holder.INSTANCE }
    }

    @Inject
    lateinit var loginPresenter: LoginPresenter
    private var isVisiblePassword = false
    private val ROTATION_KEY = "rotate"
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
        /*fragmentManager!!.beginTransaction()
            .replace(R.id.content_layout, SettingsFragment.instance).commit()*/

        Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(R.id.settingsFragment)
    }

    override fun onLoginError() {
        /*login_btn.doneLoadingAnimation(
            R.color.colorCompleteProgress,
            getBitmap(activity as Context, R.drawable.ic_error_black)
        )*/

        toast(activity as Context, getString(R.string.error_login_toast))
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
        login_btn.isEnabled = false

        views.forEach {
            it.animate(activity as Context, startOffset)
            startOffset += 250
        }

        rotate = false
    }

    private fun initViewsOptions() {
        initClickListenerForLoginButton()
        initFocusChangeListenerForPassET()
        initClickListenerForShowPassButton()
    }

    private fun initClickListenerForLoginButton() {
        login_btn.setOnClickListener {
            hideKeyboard(activity)
            login_layout.requestFocus()
            Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(R.id.action_loginFragment_to_settingsFragment)
           /* fragmentManager!!.beginTransaction()
                .replace(R.id.content_layout, SettingsFragment.instance).commit()*/

            /*login_layout.requestFocus()
            hideKeyboard(activity)

            loginPresenter.logIn(email_et.text.toString(), password_et.text.toString())*/
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
                password_et.transformationMethod = HideReturnsTransformationMethod.getInstance()
                isVisiblePassword = true
            } else {
                password_et.transformationMethod = PasswordTransformationMethod.getInstance()
                isVisiblePassword = false
            }
        }
    }
}