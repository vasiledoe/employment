package features.splash.model

import com.google.firebase.auth.FirebaseAuth
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.MyLogs


class SplashRepo : KoinComponent {

    private val logs: MyLogs by inject()

    private val authInstance by lazy {
        FirebaseAuth.getInstance()
    }


    fun silentSignIn(listener: OnSilentAuthListener) {
        val user = authInstance.currentUser

        if (user != null) {
            logs.LOG("SplashRepo", "silentSignIn", "success")
            listener.onSilentAuth(true)

        } else {
            logs.LOG("SplashRepo", "silentSignIn", "not logged")
            listener.onSilentAuth(false)
        }
    }
}