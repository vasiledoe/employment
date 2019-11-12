package features.auth.viewmodel

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bitplanet.employment.R
import features.auth.model.AuthRepo
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.ResUtil

class AuthViewModel : ViewModel(), KoinComponent {

    val isLogged = MutableLiveData<Boolean>()
    val isRecoverySent = MutableLiveData<Boolean>()

    val error = MutableLiveData<String>()
    val isProgressLoading = MutableLiveData<Boolean>()
    val goToFrgId = MutableLiveData<Int>()


    private val authRepo: AuthRepo by inject()
    private val resUtil: ResUtil by inject()


    fun isRegDataInserted(
        email: String,
        pass: String,
        passRepeat: String
    ): Boolean {
        if (email.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.add_warn_email_empty)
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            error.value = resUtil.getStringRes(R.string.add_warn_email_no_fit)
            return false
        }

        if (pass.replace("\\s+", "").length < 6 ||
            passRepeat.replace("\\s+", "").length < 6
        ) {
            error.value = resUtil.getStringRes(R.string.add_warn_pass_empty)
            return false
        }

        if (pass != passRepeat) {
            error.value = resUtil.getStringRes(R.string.add_warn_pass_retype)
            return false
        }

        return true
    }

    fun isLoginDataInserted(
        email: String,
        pass: String
    ): Boolean {
        if (email.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.add_warn_email_empty)
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            error.value = resUtil.getStringRes(R.string.add_warn_email_no_fit)
            return false
        }

        if (pass.replace("\\s+", "").length < 6) {
            error.value = resUtil.getStringRes(R.string.add_warn_pass_empty)
            return false
        }

        return true
    }

    fun isRecoveryDataInserted(email: String): Boolean {
        if (email.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.add_warn_email_empty)
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            error.value = resUtil.getStringRes(R.string.add_warn_email_no_fit)
            return false
        }

        return true
    }


    fun doCreateAccount(
        email: String,
        pass: String
    ) {
        isProgressLoading.value = true

        authRepo.createAccount(
            email = email,
            password = pass,
            successListener = { isLogged.value = true },
            errListener = { err -> error.value = err }
        )
    }

    fun doLogin(email: String, pass: String) {
        isProgressLoading.value = true

        authRepo.signIn(
            email = email,
            password = pass,
            successListener = { isLogged.value = true },
            errListener = { err -> error.value = err }
        )
    }

    fun doRecovery(email: String) {
        isProgressLoading.value = true

        authRepo.recoveryPass(
            email = email,
            successListener = { isRecoverySent.value = true },
            errListener = { err -> error.value = err }
        )
    }

    fun goToFrg(extraId: Int) {
        goToFrgId.value = extraId
    }
}