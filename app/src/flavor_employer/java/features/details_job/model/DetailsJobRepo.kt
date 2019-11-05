package features.details_job.model

import base.model.JobUtil
import base.model.MainRepo
import com.google.firebase.firestore.FirebaseFirestore

class DetailsJobRepo : MainRepo() {

    private val dbInstance by lazy {
        FirebaseFirestore.getInstance()
    }

    fun deleteJob(id: String, successListener: () -> Unit, errListener: (err: String?) -> Unit) {
        dbInstance.collection(JobUtil.KEY_JOBS)
            .document(id)
            .delete()
            .addOnSuccessListener { successListener() }
            .addOnFailureListener { e -> errListener(e.message) }
    }
}