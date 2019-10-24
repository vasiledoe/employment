package base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


open class BaseActivity : AppCompatActivity() {

    protected fun showSnack(coordinator: CoordinatorLayout, txt: String, isLongShow: Boolean) {
        Snackbar.make(
            coordinator,
            txt,
            if (isLongShow) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
        ).show()
    }

    protected fun showLoading(viewLoading: View) {
        viewLoading.visibility = View.VISIBLE
    }

    protected fun hideLoading(viewLoading: View) {
        viewLoading.visibility = View.GONE
    }

    protected fun switchFrgFromActivity(
        fragment: Fragment,
        isAddedToBackStack: Boolean,
        idContainer: Int,
        frgTag: String
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(idContainer, fragment)

        //add frg to system backstack
        if (isAddedToBackStack) {
            fragmentTransaction.addToBackStack(frgTag)
        }

        fragmentTransaction.commitAllowingStateLoss()
    }
}