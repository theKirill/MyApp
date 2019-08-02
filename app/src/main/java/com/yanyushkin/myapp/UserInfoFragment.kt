package com.yanyushkin.myapp

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.toolbar_user_info.*

class UserInfoFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_user_info, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListenerForButtons()
    }

    private fun initClickListenerForButtons() {
        back_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_userInfoFragment2_to_settingsFragment_back))

        ok_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_userInfoFragment2_to_settingsFragment_ok))

        user_logout_btn.setOnClickListener {
            Firebase.mAuth.signOut()

            Navigation.findNavController(activity as Activity, R.id.main_nav_host_fragment)
                .navigate(R.id.action_userInfoFragment2_to_settingsFragment_ok)
        }
    }
}