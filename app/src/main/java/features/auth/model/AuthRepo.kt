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


    fun createAccount(
        email: String,
        password: String,
        successListener: () -> Unit,
        errListener: (err: String?) -> Unit
    ) {
        authInstance.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    logs.LOG("AuthRepo", "createAccount", "success")
                    successListener()

                } else {
                    logs.LOG("AuthRepo", "createAccount", "ERR:" + it.exception)
                    errListener(it.exception.toString())
                }
            }
    }

    fun signIn(
        email: String,
        password: String,
        successListener: () -> Unit,
        errListener: (err: String?) -> Unit
    ) {
        authInstance.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    logs.LOG("AuthRepo", "signIn", "success")
                    successListener()

                } else {
                    logs.LOG("AuthRepo", "signIn", "ERR:" + it.exception)
                    errListener(it.exception.toString())
                }
            }
    }

    fun recoveryPass(
        email: String,
        successListener: () -> Unit,
        errListener: (err: String?) -> Unit
    ) {
        authInstance.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    logs.LOG("AuthRepo", "recoveryPass", "success")
                    successListener()

                } else {
                    logs.LOG("AuthRepo", "recoveryPass", "ERR:" + it.exception)
                    errListener(it.exception.toString())
                }
            }
    }

}