package features.create_job.model

import base.model.OnErrDbListener

interface OnInsertJobListener : OnErrDbListener {

    fun onInsertJobSuccess()
}