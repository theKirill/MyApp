package com.yanyushkin.myapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.yanyushkin.myapp.App
import com.yanyushkin.myapp.Firebase
import com.yanyushkin.myapp.R
import com.yanyushkin.myapp.arch.AccountContract
import com.yanyushkin.myapp.extensions.hide
import com.yanyushkin.myapp.extensions.show
import com.yanyushkin.myapp.presenters.AccountPresenter
import kotlinx.android.synthetic.main.fragment_account.*
import javax.inject.Inject

/**
 * Фрагмент вывода краткой информации о пользователе на вкладке настроек
 */
class AccountFragment : Fragment(), AccountContract.View {

    @Inject
    lateinit var accountPresenter: AccountPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

        App.component.injectsAccountFragment(this)
        accountPresenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_account, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountPresenter.checkUserAvailability(Firebase.mAuth.currentUser)

        initClickListenersForViews()
    }

    override fun showAccount(email: String) {
        no_account_layout.hide()
        account_layout.show()
        email_tv.text = email
    }

    private fun initClickListenersForViews() {
        main_login_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settingsFragment_to_loginActivity))

        main_sign_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settingsFragment_to_signInActivity))

        about_iv.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settingsFragment_to_userInfoActivity))

        account_layout.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settingsFragment_to_userInfoActivity))
    }
}