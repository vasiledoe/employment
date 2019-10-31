package features.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bitplanet.employment.BuildConfig
import com.bitplanet.employment.MainActivity
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import features.auth.view.AuthActivity
import features.splash.viewmodel.SplashViewModel
import io.fabric.sdk.android.Fabric


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mViewModel: SplashViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeCrashlytics()

        onBindModel()

        mViewModel.doSilentSignIn()
    }

    private fun initializeCrashlytics() {
        val core = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(core).build())
    }

    private fun onBindModel() {
        mViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        mViewModel.isLogged.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))

            } else {
                startActivity(Intent(this, AuthActivity::class.java))
            }

            finish()
        })
    }
}