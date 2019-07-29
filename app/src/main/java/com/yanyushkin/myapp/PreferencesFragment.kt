package com.yanyushkin.myapp

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?): Unit =
        addPreferencesFromResource(R.xml.preference)
}