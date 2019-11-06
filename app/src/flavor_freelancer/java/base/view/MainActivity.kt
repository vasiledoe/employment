package com.bitplanet.employment

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import base.view.BaseMainActivity
import base.view.CustomDialogs
import base.view_model.FlavorViewModel
import features.details_job.view.DetailsFrg
import features.edit_talent_account.view.EditTalentFrg
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseMainActivity() {

    private lateinit var mViewModel: FlavorViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()

        onBindModel()

        fillUpInitialData()
    }

    private fun fillUpInitialData() {
        mViewModel.showLoggeUserEmail()
        onNavigationItemSelected(mNavigationView.menu.findItem(R.id.nav_home))
    }

    private fun onBindModel() {
        mViewModel = ViewModelProviders.of(this).get(FlavorViewModel::class.java)

        mViewModel.toolbarTitle.observe(this, Observer {
            supportActionBar?.title = it
        })

        mViewModel.loggedEmail.observe(this, Observer {
            mTvLoggedUserEmail.text = it
        })

        mViewModel.goToFrgId.observe(this, Observer {
            openDesiredFrg(it)
        })

        mViewModel.isTalentEditedSuccess.observe(this, Observer {
            this.onBackPressed()
        })

        mViewModel.jobDetails.observe(this, Observer {
            switchFrgFromActivity(
                DetailsFrg.newInstance(it),
                true,
                R.id.container_for_fragments,
                "DetailsFrg"
            )
        })

        mViewModel.isProgressLoading.observe(this, Observer {
            if (it) {
                showLoading(progress_bar)

            } else {
                hideLoading(progress_bar)
            }
        })

        mViewModel.error.observe(this, Observer {
            hideLoading(viewLoading = progress_bar)

            CustomDialogs().showSimpleDialog(
                activityCtx = this,
                title = resUtil.getStringRes(R.string.txt_error),
                msg = it
            )
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Handler().postDelayed({
            when (item.itemId) {
                R.id.nav_home -> {
                    openDesiredFrg(JOBS_FRG)
                }

                R.id.nav_talent_account -> {
                    openSpecificFlavorDesiredFrg(EDIT_TALENT_FRG)
                }

                R.id.nav_settings -> {
                    openDesiredFrg(SETTINGS_FRG)
                }

                R.id.nav_share -> {
                    shareAppContent()
                }

                R.id.nav_send -> {
                    contactUs()
                }
            }
        }, 400)

        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun openSpecificFlavorDesiredFrg(whereToGo: Int) {
        when (whereToGo) {
            EDIT_TALENT_FRG -> switchFrgFromActivity(
                EditTalentFrg(),
                false,
                R.id.container_for_fragments,
                "EditTalentFrg"
            )
        }
    }

}
