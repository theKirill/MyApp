package com.yanyushkin.myapp.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_login.*

fun Fragment.hideKeyboard(context: FragmentActivity?) {
    val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(password_et.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}