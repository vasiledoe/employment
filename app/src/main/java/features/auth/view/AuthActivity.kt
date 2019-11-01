package features.auth.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import base.BaseActivity
import base.CustomDialogs
import com.bitplanet.employment.MainActivity
import com.bitplanet.employment.R
import features.auth.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_sample.*


class AuthActivity : BaseActivity() {

    private lateinit var mViewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        onBindModel()

        openDesiredFrg(intent.getIntExtra(AUTH_EXTRA, LOGIN))
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun onBindModel() {
        mViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        mViewModel.isProgressLoading.observe(this, Observer {
            if (it) {
                showLoading(progress_bar)

            } else {
                hideLoading(progress_bar)
            }
        })

        mViewModel.isLogged.observe(this, Observer {
            hideLoading(progress_bar)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        mViewModel.isRecoverySent.observe(this, Observer {
            hideLoading(progress_bar)
            if (it) onBackPressed()
        })

        mViewModel.error.observe(this, Observer {
            hideLoading(viewLoading = progress_bar)

            CustomDialogs().showSimpleDialog(
                    activityCtx = this,
                    title = resUtil.getStringRes(R.string.txt_error),
                    msg = it
            )
        })

        mViewModel.goToFrgId.observe(this, Observer {
            openDesiredFrg(it)
        })
    }

}