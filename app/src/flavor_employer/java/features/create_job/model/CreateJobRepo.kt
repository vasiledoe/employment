package features.create_job.model

import base.model.Job
import base.model.JobUtil
import base.model.MainRepo
import com.google.firebase.firestore.FirebaseFirestore


class CreateJobRepo : MainRepo() {

    private val dbInstance by lazy {
        FirebaseFirestore.getInstance()
    }


    fun insertJob(job: Job, listener: OnInsertJobListener) {
        val jobMap = hashMapOf(
                JobUtil.KEY_FIELD to job.field,
                JobUtil.KEY_TITLE to job.title,
                JobUtil.KEY_DESCR to job.descr,
                JobUtil.KEY_ADRESS to job.address,
                JobUtil.KEY_PRICE to job.price,
                JobUtil.KEY_TIME to System.currentTimeMillis(),
                JobUtil.KEY_UID to getLoggedUserTk()
        )

        dbInstance.collection(JobUtil.KEY_JOBS)
                .document()
                .set(jobMap)
                .addOnSuccessListener { listener.onInsertJobSuccess() }
                .addOnFailureListener { e -> listener.onErrDb(e.message) }
    }
}