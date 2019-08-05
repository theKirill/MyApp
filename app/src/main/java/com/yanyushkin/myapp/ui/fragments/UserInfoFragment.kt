package com.yanyushkin.myapp.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yanyushkin.myapp.App
import com.yanyushkin.myapp.R
import com.yanyushkin.myapp.arch.UserInfoContract
import com.yanyushkin.myapp.presenters.UserInfoPresenter
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.toolbar_user_info.*
import javax.inject.Inject

/**
 * Фрагмент для изменения информации пользователя
 */
class UserInfoFragment : Fragment(), UserInfoContract.View {

    @Inject
    lateinit var userInfoPresenter: UserInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

        App.component.injectsUserInfoFragment(this)
        userInfoPresenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_user_info, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListenerForButtons()
    }

    override fun showSettingsFragment(): Unit =
        Navigation.findNavController(activity as Activity, R.id.user_info_nav_host_fragment)
            .navigate(R.id.action_userInfoFragment_to_mainActivity_ok)

    private fun initClickListenerForButtons() {
        back_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_userInfoFragment_to_mainActivity_back))

        ok_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_userInfoFragment_to_mainActivity_ok))

        user_logout_btn.setOnClickListener {
            userInfoPresenter.logout()
        }
    }
}