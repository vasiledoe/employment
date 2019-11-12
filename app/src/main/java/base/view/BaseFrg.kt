package base.view

import android.content.Intent
import android.net.Uri
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


    protected fun handleItmsVisibility(
        rvItms: RecyclerView,
        zoneNoItms: LinearLayout,
        status: Int
    ) {
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

    protected fun openEmail(email: String?) {
        email?.let {
            try {
                val emailIntent = Intent(
                    Intent.ACTION_SENDTO,
                    Uri.fromParts("mailto", email, null)
                )

                startActivity(emailIntent)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    protected fun openCall(phone: String?) {
        phone?.let {
            try {
                val callIntent = Intent(Intent.ACTION_VIEW)
                callIntent.data = Uri.parse("tel:$phone")
                startActivity(callIntent)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val LOADING = 0
        const val NO_ITEMS = 1
        const val HAS_ITEMS = 2
    }
}