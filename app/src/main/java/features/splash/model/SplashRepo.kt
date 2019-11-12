package features.splash.model

import com.google.firebase.auth.FirebaseAuth
import org.koin.core.KoinComponent


class SplashRepo : KoinComponent {

    private val authInstance by lazy {
        FirebaseAuth.getInstance()
    }

    fun silentSignIn(signedInListener: (isLogged: Boolean) -> Unit) {
        signedInListener(authInstance.currentUser != null)
    }
}