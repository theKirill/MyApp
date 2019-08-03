package com.yanyushkin.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yanyushkin.myapp.extensions.hide
import com.yanyushkin.myapp.extensions.show
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_account, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUserInfoForAccountLayout()

        initClickListenersForViews()
    }

    private fun setUserInfoForAccountLayout() {
        Firebase.mAuth.currentUser?.let {
            no_account_layout.hide()
            account_layout.show()
            email_tv.text = Firebase.mAuth.currentUser!!.email.toString()
        }
    }

    private fun initClickListenersForViews() {
        main_login_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settingsFragment_to_loginActivity))

        main_sign_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settingsFragment_to_signInActivity))

        about_iv.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settingsFragment_to_userInfoFragment))

        account_layout.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settingsFragment_to_userInfoFragment))
    }
}