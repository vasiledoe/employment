package features.posted_jobs.model

import base.model.OnErrDbListener

interface OnGetJobsListener : OnErrDbListener {

    fun onGetJobs(jobs: ArrayList<PostedJob>)

    fun onShowNoItems()
}