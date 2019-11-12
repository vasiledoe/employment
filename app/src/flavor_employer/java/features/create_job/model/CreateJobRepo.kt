package features.create_job.model

import base.model.Job
import base.model.JobUtil
import base.model.MainRepo
import com.google.firebase.firestore.FirebaseFirestore


class CreateJobRepo : MainRepo() {

    private val dbInstance by lazy {
        FirebaseFirestore.getInstance()
    }


    fun insertJob(
        job: Job,
        successListener: () -> Unit,
        errListener: (err: String?) -> Unit
    ) {
        val jobMap = hashMapOf(
            JobUtil.KEY_FIELD to job.field,
            JobUtil.KEY_TITLE to job.title,
            JobUtil.KEY_DESCR to job.descr,
            JobUtil.KEY_ADDRESS to job.address,
            JobUtil.KEY_PHONE to job.phone,
            JobUtil.KEY_PRICE to job.price,
            JobUtil.KEY_TIME to System.currentTimeMillis(),
            JobUtil.KEY_UID to getLoggedUserTk(),
            JobUtil.KEY_EMAIL to getLoggedUserEmail(),
            JobUtil.KEY_SEEN to 0
        )

        dbInstance.collection(JobUtil.KEY_JOBS)
            .document()
            .set(jobMap)
            .addOnSuccessListener { successListener() }
            .addOnFailureListener { e -> errListener(e.message) }
    }
}