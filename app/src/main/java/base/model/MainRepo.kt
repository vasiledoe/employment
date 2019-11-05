package base.model

import com.google.firebase.auth.FirebaseAuth

open class MainRepo {

    private val authInstance by lazy {
        FirebaseAuth.getInstance()
    }

    fun getLoggedUserEmail() = authInstance.currentUser?.email ?: "guest"

    fun getLoggedUserTk() = authInstance.currentUser?.uid ?: "guest"

}