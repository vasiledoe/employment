package features.auth.model

import base.model.OnErrDbListener

interface OnAuthListener : OnErrDbListener {

    fun onAuthSuccess()

}