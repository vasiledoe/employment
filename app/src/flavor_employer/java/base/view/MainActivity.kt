package com.bitplanet.employment

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import base.view.BaseActivity
import base.view.CustomDialogs
import base.view_model.BaseViewModel
import com.google.android.material.navigation.NavigationView
import features.details_job.view.DetailsFrg
import kotlinx.android.synthetic.main.activity_sample.*

class MainActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private lateinit var mToolbar: Toolbar
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private lateinit var mTvLoggedUserEmail: TextView

    private lateinit var mViewModel: BaseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()

        onBindModel()

        fillUpInitialData()
    }


    private fun setupViews() {
        setupDrawer()

        val headerView = mNavigationView.getHeaderView(0)
        mTvLoggedUserEmail = headerView.findViewById(R.id.tv_email)
    }

    private fun setupDrawer() {
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)

        mDrawerLayout = findViewById(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            mToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        mNavigationView = findViewById(R.id.nav_view)
        mNavigationView.setNavigationItemSelectedListener(this)
    }

    private fun fillUpInitialData() {
        mViewModel.showLoggeUserEmail()
        onNavigationItemSelected(mNavigationView.menu.findItem(R.id.nav_home))
    }

    private fun onBindModel() {
        mViewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)

        mViewModel.toolbarTitle.observe(this, Observer {
            supportActionBar?.title = it
        })

        mViewModel.loggedEmail.observe(this, Observer {
            mTvLoggedUserEmail.text = it
        })

        mViewModel.goToFrgId.observe(this, Observer {
            openDesiredFrg(it)
        })

        mViewModel.postedJobDetails.observe(this, Observer {
            switchFrgFromActivity(
                DetailsFrg.newInstance(it),
                true,
                R.id.container_for_fragments,
                "DetailsFrg"
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


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        logs.LOG("MainActivity", "onNavigationItemSelected", "...")
        Handler().postDelayed({
            when (item.itemId) {
                R.id.nav_home -> {
                    openDesiredFrg(JOBS_FRG)
                }

                R.id.nav_talents -> {
                    openDesiredFrg(TALENTS_FRG)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_logout -> {
                doSignOut()
            }
        }
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)

        } else {
            super.onBackPressed()
        }
    }
}
