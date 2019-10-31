package features.auth.model

import com.google.firebase.auth.FirebaseAuth
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.MyLogs


class AuthRepo : KoinComponent {

    private val logs: MyLogs by inject()

    private val authInstance by lazy {
        FirebaseAuth.getInstance()
    }


    fun createAccount(email: String, password: String, listener: OnAuthListener) {
        authInstance.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        logs.LOG("AuthRepo", "createAccount", "success")
                        listener.onAuthSuccess()

                    } else {
                        logs.LOG("AuthRepo", "createAccount", "ERR:" + it.exception)
                        listener.onErrDb(it.exception.toString())
                    }
                }
    }

    fun signIn(email: String, password: String, listener: OnAuthListener) {
        authInstance.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        logs.LOG("AuthRepo", "signIn", "success")
                        listener.onAuthSuccess()

                    } else {
                        logs.LOG("AuthRepo", "signIn", "ERR:" + it.exception)
                        listener.onErrDb(it.exception.toString())
                    }
                }
    }

    fun recoveryPass(email: String, listener: OnRecoveryPassListener) {
        authInstance.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        logs.LOG("AuthRepo", "recoveryPass", "success")
                        listener.onRecoverySuccess()

                    } else {
                        logs.LOG("AuthRepo", "recoveryPass", "ERR:" + it.exception)
                        listener.onErrDb(it.exception.toString())
                    }
                }
    }

}