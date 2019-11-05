package base.view

import android.content.Context
import androidx.appcompat.app.AlertDialog
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.ResUtil


class CustomDialogs : KoinComponent {

    private val resUtil: ResUtil by inject()

    /**
     * make sure to show dialog only if activityCtx not null
     */
    fun showSimpleDialog(
            activityCtx: Context?,
            title: String,
            msg: String,
            haveCancel: Boolean? = false,
            customOkTxt: String? = null,
            customCancelTxt: String? = null,
            callbackYes: (() -> Unit)? = null,
            callbackNo: (() -> Unit)? = null
    ) {
        activityCtx?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
            builder.setMessage(msg)

            val okTxt = customOkTxt ?: resUtil.getStringRes(android.R.string.yes)
            builder.setPositiveButton(okTxt) { dialog, id ->
                dialog.dismiss()
                callbackYes?.invoke()
            }

            if (haveCancel == true) {
                val noText = customCancelTxt ?: resUtil.getStringRes(android.R.string.cancel)
                builder.setNegativeButton(noText) { dialog, id ->
                    dialog.dismiss()
                    callbackNo?.invoke()
                }
            }

            val dialog = builder.create()
            dialog.setCancelable(false)
            dialog.show()
        }
    }
}