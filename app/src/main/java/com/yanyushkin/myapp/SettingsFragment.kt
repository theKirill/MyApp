package com.yanyushkin.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    private object Holder {
        val INSTANCE = SettingsFragment()
    }

    companion object {
        val instance: SettingsFragment by lazy { Holder.INSTANCE }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_settings, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentManager!!.beginTransaction().replace(R.id.account_info_layout, AccountFragment()).commit()
        fragmentManager!!.beginTransaction().replace(R.id.preferences_layout, PreferencesFragment()).commit()
    }
}