package features.auth.model

import base.OnErrDbListener

interface OnAuthListener : OnErrDbListener {

    fun onAuthSuccess()

}