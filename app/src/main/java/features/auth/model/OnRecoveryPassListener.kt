package features.auth.model

interface OnRecoveryPassListener : OnErrDbListener {

    fun onRecoverySuccess()
}