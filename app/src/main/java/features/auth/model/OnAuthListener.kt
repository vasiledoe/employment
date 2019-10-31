package features.auth.model

interface OnAuthListener : OnErrDbListener {

    fun onAuthSuccess()

}