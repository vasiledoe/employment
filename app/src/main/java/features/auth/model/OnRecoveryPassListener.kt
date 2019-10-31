package features.auth.model

import base.OnErrDbListener

interface OnRecoveryPassListener : OnErrDbListener {

    fun onRecoverySuccess()
}