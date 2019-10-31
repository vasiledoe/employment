package com.bitplanet.employment

import android.annotation.SuppressLint
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
import base.BaseActivity
import base.CustomDialogs
import base.viewModel.BaseViewModel
import com.google.android.material.navigation.NavigationView
import features.posted_jobs.view.JobsFrg
import features.settings.view.SettingsFrg
import kotlinx.android.synthetic.main.activity_sample.*

class MainActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private lateinit var mToolbar: Toolbar
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mTvLoggedUserEmail: TextView

    private lateinit var mViewModel: BaseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()

        onBindModel()
    }

    @SuppressLint("RestrictedApi")
    private fun setupViews() {
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

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val headerView = navigationView.getHeaderView(0)
        mTvLoggedUserEmail = headerView.findViewById(R.id.tv_email)

        onNavigationItemSelected(navigationView.menu.findItem(R.id.nav_home))
    }

    private fun onBindModel() {
        mViewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)

        mViewModel.toolbarTitle.observe(this, Observer {
            logs.LOG("dasd", "dsf", "23784203567254: $it")
            supportActionBar?.title = it
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
                    switchFrgFromActivity(
                        JobsFrg(),
                        true,
                        R.id.container_for_fragments,
                        "JobsFrg"
                    )
                }

                R.id.nav_settings -> {
                    switchFrgFromActivity(
                        SettingsFrg(),
                        true,
                        R.id.container_for_fragments,
                        "SettingsFrg"
                    )
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
