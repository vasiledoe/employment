package features.list_jobs.model

import base.model.JobUtil
import base.model.MainRepo
import base.model.PrettyFormattedJob
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.DataFormatter

class ListJobsRepo : MainRepo(), KoinComponent {

    private val dbInstance by lazy {
        FirebaseFirestore.getInstance()
    }

    private val dataFormatter: DataFormatter by inject()


    fun getJobs(
        receivedIJobsListener: (jobs: ArrayList<PrettyFormattedJob>) -> Unit,
        noItemsListener: () -> Unit,
        errListener: (err: String?) -> Unit
    ) {
        dbInstance.collection(JobUtil.KEY_JOBS)
            .whereEqualTo(JobUtil.KEY_UID, getLoggedUserTk())
            .get()
            .addOnSuccessListener { documents ->
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