package features.settings.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceFragmentCompat
import base.view_model.FlavorViewModel
import com.bitplanet.employment.R


class SettingsFrg : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        onBindModel()
    }

    private fun onBindModel() {
        activity?.let {
            val mViewModel = ViewModelProviders.of(it).get(FlavorViewModel::class.java)
            mViewModel.setToolbarTitle(resources.getString(R.string.menu_settings))
        }
    }

}