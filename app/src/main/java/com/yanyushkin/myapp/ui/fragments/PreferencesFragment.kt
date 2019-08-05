package com.yanyushkin.myapp.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.yanyushkin.myapp.R

/**
 * Фрагмент внутренних настроек приложения
 */
class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?): Unit =
        addPreferencesFromResource(R.xml.preference)
}