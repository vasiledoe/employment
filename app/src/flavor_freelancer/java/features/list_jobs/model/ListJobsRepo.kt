package features.list_jobs.model

import base.model.JobUtil
import base.model.MainRepo
import base.model.PrettyFormattedJob
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.DataFormatter

class ListJobsRepo : MainRepo(), KoinComponent {

    private val dbInstance by lazy {
        FirebaseFirestore.getInstance()
    }

    private val dataFormatter: DataFormatter by inject()


    fun getJobs(
        fieldIds: Int,
        receivedIJobsListener: (jobs: ArrayList<PrettyFormattedJob>) -> Unit,
        noItemsListener: () -> Unit,
        errListener: (err: String?) -> Unit
    ) {
        val query: Task<QuerySnapshot> = if (fieldIds == 0) {
            dbInstance.collection(JobUtil.KEY_JOBS)
                .get()

        } else {
            dbInstance.collection(JobUtil.KEY_JOBS)
                .whereEqualTo(JobUtil.KEY_FIELD, fieldIds)
                .get()
        }

        query.addOnSuccessListener { documents ->
            if (documents != null) {
                receivedIJobsListener(dataFormatter.getJobsFromSnapshot(documents))

            } else {
                noItemsListener()
            }
        }
            .addOnFailureListener { exception ->
                errListener(exception.message)
                noItemsListener()
            }
    }
}