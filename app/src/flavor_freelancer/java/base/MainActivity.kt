package com.bitplanet.employment

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mTvLoggedUserEmail: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
    }

    private fun setupViews() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mDrawerLayout = findViewById(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val headerView = navigationView.getHeaderView(0)
        mTvLoggedUserEmail = headerView.findViewById(R.id.tv_email)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Handler().postDelayed({
            when (item.itemId) {
                R.id.nav_home -> {
                    //todo
                }

                R.id.nav_proposals -> {
                    //todo
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
