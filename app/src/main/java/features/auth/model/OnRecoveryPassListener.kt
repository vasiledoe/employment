package features.auth.model

import base.model.OnErrDbListener

interface OnRecoveryPassListener : OnErrDbListener {

    fun onRecoverySuccess()
}