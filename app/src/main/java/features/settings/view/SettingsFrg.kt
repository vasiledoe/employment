package features.settings.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceFragmentCompat
import base.viewModel.BaseViewModel
import com.bitplanet.employment.R
import features.auth.viewmodel.AuthViewModel


class SettingsFrg : PreferenceFragmentCompat() {

    private lateinit var mViewModel: BaseViewModel


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        onBindModel()
    }

    private fun onBindModel() {
        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(BaseViewModel::class.java)
            mViewModel.setToolbarTitle(resources.getString(R.string.menu_settings))
        }
    }

}