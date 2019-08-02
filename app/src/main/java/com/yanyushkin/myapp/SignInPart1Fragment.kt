package com.yanyushkin.myapp

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
import com.yanyushkin.myapp.extensions.*
import com.yanyushkin.myapp.presenters.SignInPart1Presenter
import com.yanyushkin.myapp.views.SignInView
import kotlinx.android.synthetic.main.fragment_signin.*
import javax.inject.Inject

/**
 * Фрагмент для ввода почты/пароля
 */
class SignInPart1Fragment : Fragment(), SignInView {

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

    override fun onSignInSuccessful(): Unit =
        Navigation.findNavController(activity as Activity, R.id.sign_nav_host_fragment)
            .navigate(R.id.action_signInPart1Fragment_to_signInPart2Fragment)

    override fun onSignInError(): Unit = toast(activity as Context, getString(R.string.error_sign_message))

    override fun showProgress(): Unit = next_btn.startAnimation()

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
        next_btn.isEnabled = false

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
            sign_layout.requestFocus()
            hideKeyboard(activity, next_btn)

            signInPresenter.signIn(sign_email_et.text.toString(), sign_password_et.text.toString())
        }
    }

    private fun initTextChangeListenerForEmailET() {
        sign_email_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isEmailValid(sign_email_et.text.toString())) {
                    sign_email_done_iv.show()
                    sign_email_done_iv.setImageResource(R.drawable.ic_done)
                    sign_email_et.background = resources.getDrawable(R.drawable.ok_rounded_view, activity!!.theme)
                    next_btn.isEnabled = isPasswordValid(sign_password_et.text.toString())
                } else {
                    if (sign_email_et.text!!.isEmpty()) {
                        sign_email_done_iv.hide()
                        sign_email_et.background = resources.getDrawable(R.drawable.rounded_view, activity!!.theme)
                    } else {
                        sign_email_done_iv.show()
                        sign_email_done_iv.setImageResource(R.drawable.ic_error)
                        sign_email_et.background =
                            resources.getDrawable(R.drawable.error_rounded_view, activity!!.theme)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initTextChangeListenerForPassET() {
        sign_password_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isPasswordValid(sign_password_et.text.toString())) {
                    sign_password_et.background = resources.getDrawable(R.drawable.ok_rounded_view, activity!!.theme)
                    next_btn.isEnabled = isEmailValid(sign_email_et.text.toString())
                } else {
                    if (sign_password_et.text!!.isEmpty())
                        sign_password_et.background = resources.getDrawable(R.drawable.rounded_view, activity!!.theme)
                    else
                        sign_password_et.background =
                            resources.getDrawable(R.drawable.error_rounded_view, activity!!.theme)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initFocusChangeListenerForPassET() {
        sign_password_et.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                sign_show_password_btn.show()
                sign_password_done_iv.hide()
            } else {
                sign_show_password_btn.hide()

                if (sign_password_et.text!!.isNotEmpty()) {
                    sign_password_done_iv.show()

                    if (isPasswordValid(sign_password_et.text.toString())) {
                        sign_password_done_iv.setImageResource(R.drawable.ic_done)
                    } else {
                        sign_password_done_iv.setImageResource(R.drawable.ic_error)
                        toast(activity as Context, resources.getString(R.string.info_password_message))
                    }
                }
            }
        }
    }

    private fun initClickListenerForShowPassButton() {
        sign_show_password_btn.setOnClickListener {
            if (!isVisiblePassword) {
                sign_password_et.showPassword()
                isVisiblePassword = true
            } else {
                sign_password_et.hidePassword()
                isVisiblePassword = false
            }
        }
    }

    private fun isEmailValid(email: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid(password: String): Boolean = password.length > 6
}