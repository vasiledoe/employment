package base

import android.view.View
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import utils.MyLogs
import utils.ResUtil

open class BaseFrg : Fragment() {

    protected val resUtil: ResUtil by inject()
    protected val logs: MyLogs by inject()


    protected fun hideLoading(viewLoading: View) {
        viewLoading.visibility = View.GONE
    }


    protected fun showLoading(viewLoading: View) {
        viewLoading.visibility = View.VISIBLE
    }

}