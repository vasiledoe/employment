package base.view

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import base.view_model.FlavorViewModel
import com.bitplanet.employment.R
import features.create_job.view.CreateJobFrg
import features.details_job.view.DetailsFrg
import features.details_talent.view.DetailsTalentFrg
import features.list_talents.view.TalentsFrg
import kotlinx.android.synthetic.main.activity_sample.*

class DrawerActivity : BaseDrawerActivity() {

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
        mNavigationView.menu.getItem(0).isChecked = true
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
            //check if these frgs are specific only to this flavor
            if (it == CREATE_JOB_FRG || it == TALENTS_FRG) {
                openSpecificFlavorDesiredFrg(it)

            } else {
                openDesiredFrg(it)
            }
        })

        mViewModel.jobDetails.observe(this, Observer {
            switchFrgFromActivity(
                DetailsFrg.newInstance(it),
                true,
                R.id.container_for_fragments,
                "DetailsFrg"
            )
        })

        mViewModel.talentDetails.observe(this, Observer {
            switchFrgFromActivity(
                DetailsTalentFrg.newInstance(it),
                true,
                R.id.container_for_fragments,
                "DetailsTalentFrg"
            )
        })

        mViewModel.isJobPostedSuccess.observe(this, Observer {
            this.onBackPressed()
        })

        mViewModel.isJobDeletedSuccess.observe(this, Observer {
            this.onBackPressed()
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


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        Handler().postDelayed({
            when (p0.itemId) {
                R.id.nav_home -> {
                    openDesiredFrg(JOBS_FRG)
                }

                R.id.nav_talents -> {
                    openSpecificFlavorDesiredFrg(TALENTS_FRG)
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


    private fun openSpecificFlavorDesiredFrg(whereToGo: Int) {
        when (whereToGo) {
            CREATE_JOB_FRG -> switchFrgFromActivity(
                CreateJobFrg(),
                true,
                R.id.container_for_fragments,
                "CreateJobFrg"
            )

            TALENTS_FRG -> switchFrgFromActivity(
                TalentsFrg(),
                false,
                R.id.container_for_fragments,
                "TalentsFrg"
            )
        }
    }

}
