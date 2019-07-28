package com.yanyushkin.myapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    override fun showProgress(): Unit = login_btn.startAnimation()

    override fun onLogInSuccessful() {
        /*login_btn.doneLoadingAnimation(
            R.color.colorCompleteProgress,
            getBitmap(activity as Context, R.drawable.ic_complete)
        )*/
    }

    override fun onLogInError() {
        /*login_btn.doneLoadingAnimation(
            R.color.colorCompleteProgress,
            getBitmap(activity as Context, R.drawable.ic_error_black)
        )*/

        toast(activity as Context, getString(R.string.error_login_toast))
    }

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
        initTextChangeListenerForEmailET()
        initTextChangeListenerForPassET()
        initFocusChangeListenerForPassET()
        initClickListenerForShowPassButton()
    }

    private fun initClickListenerForLoginButton() {
        login_btn.setOnClickListener {
            login_layout.requestFocus()
            hideKeyboard(activity)

            loginPresenter.logIn(email_et.text.toString(), password_et.text.toString())
        }
    }

    private fun initTextChangeListenerForEmailET() {
        email_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isEmailValid(email_et.text.toString())) {
                    email_done_iv.show()
                    email_done_iv.setImageResource(R.drawable.ic_done)
                    email_et.background = resources.getDrawable(R.drawable.ok_rounded_view, activity!!.theme)
                    login_btn.isEnabled = isPasswordValid(password_et.text.toString())
                } else {
                    if (email_et.text!!.isEmpty()) {
                        email_done_iv.hide()
                        email_et.background = resources.getDrawable(R.drawable.rounded_view, activity!!.theme)
                    } else {
                        email_done_iv.show()
                        email_done_iv.setImageResource(R.drawable.ic_error)
                        email_et.background = resources.getDrawable(R.drawable.error_rounded_view, activity!!.theme)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initTextChangeListenerForPassET() {
        password_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isPasswordValid(password_et.text.toString())) {
                    password_et.background = resources.getDrawable(R.drawable.ok_rounded_view, activity!!.theme)
                    login_btn.isEnabled = isEmailValid(email_et.text.toString())
                } else {
                    if (password_et.text!!.isEmpty())
                        password_et.background = resources.getDrawable(R.drawable.rounded_view, activity!!.theme)
                    else
                        password_et.background = resources.getDrawable(R.drawable.error_rounded_view, activity!!.theme)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initFocusChangeListenerForPassET() {
        password_et.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                show_password_btn.show()
            } else {
                show_password_btn.hide()

                if (password_et.text!!.isNotEmpty()) {
                    password_done_iv.show()

                    if (isPasswordValid(password_et.text.toString())) {
                        password_done_iv.setImageResource(R.drawable.ic_done)
                    } else {
                        password_done_iv.setImageResource(R.drawable.ic_error)
                        //toast(activity as Context, resources.getString(R.string.info_password_toast))
                    }
                }
            }
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

    private fun isEmailValid(email: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid(password: String): Boolean = password.length > 6
}