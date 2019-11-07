package base.view

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import base.model.DefConstants
import com.bitplanet.employment.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import features.auth.view.AuthActivity
import features.auth.view.LoginFrg
import features.auth.view.RecoveryFrg
import features.auth.view.RegisterFrg
import features.list_jobs.view.JobsFrg
import features.settings.view.SettingsFrg
import org.koin.android.ext.android.inject
import utils.MyLogs
import utils.ResUtil


open class BaseActivity : AppCompatActivity() {

    val logs: MyLogs by inject()
    protected val resUtil: ResUtil by inject()


    protected fun showSnack(
        coordinator: CoordinatorLayout,
        txt: String,
        isLongShow: Boolean
    ) {
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

        fragmentTransaction.setCustomAnimations(
            R.animator.fade_in,
            R.animator.fade_out,
            R.animator.fade_in,
            R.animator.fade_out
        )

        fragmentTransaction.replace(idContainer, fragment, frgTag)

        //add frg to system backstack
        if (isAddedToBackStack) {
            fragmentTransaction.addToBackStack(frgTag)

        }else{
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        fragmentTransaction.commitAllowingStateLoss()
    }

    fun contactUs() {
        try {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", DefConstants.SUPPORT_EMAIL, null)
            )
            emailIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                resUtil.getStringRes(R.string.app_name)
            )
            startActivity(emailIntent)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun shareAppContent() {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"

            intent.putExtra(Intent.EXTRA_SUBJECT, resUtil.getStringRes(R.string.share_subject_txt))
            intent.putExtra(
                Intent.EXTRA_TEXT,
                resUtil.getStringRes(R.string.share_message_body) + " at https://play.google.com/store/apps/details?id=" + packageName
            )
            startActivity(intent)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun doSignOut() {
        CustomDialogs().showSimpleDialog(
            activityCtx = this,
            title = resUtil.getStringRes(R.string.txt_caution),
            msg = resUtil.getStringRes(R.string.txt_warn_logout),
            haveCancel = true,
            callbackYes = {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        )
    }

    fun openDesiredFrg(whereToGo: Int) {
        when (whereToGo) {
            REGISTER -> switchFrgFromActivity(
                RegisterFrg(),
                true,
                R.id.container_for_fragments,
                "RegisterFrg"
            )

            RECOVERY -> switchFrgFromActivity(
                RecoveryFrg(),
                true,
                R.id.container_for_fragments,
                "RecoveryFrg"
            )

            LOGIN -> switchFrgFromActivity(
                LoginFrg(),
                false,
                R.id.container_for_fragments,
                "LoginFrg"
            )

            JOBS_FRG -> switchFrgFromActivity(
                JobsFrg(),
                false,
                R.id.container_for_fragments,
                "JobsFrg"
            )

            SETTINGS_FRG -> switchFrgFromActivity(
                SettingsFrg(),
                false,
                R.id.container_for_fragments,
                "SettingsFrg"
            )
        }
    }


    companion object {
        val AUTH_EXTRA = "AUTH_EXTRA"
        val LOGIN = 1
        val REGISTER = 2
        val RECOVERY = 3

        val CREATE_JOB_FRG = 4
        val EDIT_TALENT_FRG = 5
        val JOBS_FRG = 6
        val TALENTS_FRG = 7
        val TALENTS_details_FRG = 8
        val SETTINGS_FRG = 9
    }
}