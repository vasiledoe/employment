package features.settings.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceFragmentCompat
import base.viewModel.BaseViewModel
import com.bitplanet.employment.R


class SettingsFrg : PreferenceFragmentCompat() {

    private lateinit var mViewModel: BaseViewModel


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        onBindModel()

        mViewModel.setToolbarTitle(resources.getString(R.string.menu_settings))
    }

    private fun onBindModel() {
        mViewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)
    }

}