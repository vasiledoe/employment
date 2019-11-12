package base.view

import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bitplanet.employment.R
import com.google.android.material.navigation.NavigationView

open class BaseDrawerActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private lateinit var mToolbar: Toolbar
    protected lateinit var mDrawerLayout: DrawerLayout
    protected lateinit var mNavigationView: NavigationView
    protected lateinit var mTvLoggedUserEmail: TextView


    fun setupViews() {
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


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return false
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