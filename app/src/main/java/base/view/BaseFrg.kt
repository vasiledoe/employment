package base.view

import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import utils.DataFormatter
import utils.MyLogs
import utils.ResUtil

open class BaseFrg : Fragment() {

    protected val resUtil: ResUtil by inject()
    protected val logs: MyLogs by inject()
    protected val formatter: DataFormatter by inject()


    protected fun handleItmsVisibility(rvItms: RecyclerView, zoneNoItms: LinearLayout, status: Int) {
        when (status) {
            LOADING -> {
                rvItms.visibility = View.GONE
                zoneNoItms.visibility = View.GONE
            }

            NO_ITEMS -> {
                rvItms.visibility = View.GONE
                zoneNoItms.visibility = View.VISIBLE
            }

            HAS_ITEMS -> {
                rvItms.visibility = View.VISIBLE
                zoneNoItms.visibility = View.GONE
            }
        }
    }

    companion object {
        val LOADING = 0
        val NO_ITEMS = 1
        val HAS_ITEMS = 2
    }

}