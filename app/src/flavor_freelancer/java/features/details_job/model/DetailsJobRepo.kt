package features.details_job.model

import base.model.JobUtil
import base.model.MainRepo
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class DetailsJobRepo : MainRepo() {

    private val dbInstance by lazy {
        FirebaseFirestore.getInstance()
    }

    fun incrementSeenCounter(docId: String?) {
        docId?.let {
            dbInstance.collection(JobUtil.KEY_JOBS)
                .document(docId)
                .update(JobUtil.KEY_SEEN, FieldValue.increment(1))
        }
    }
}