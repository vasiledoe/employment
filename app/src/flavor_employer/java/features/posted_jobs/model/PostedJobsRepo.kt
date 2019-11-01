package features.posted_jobs.model

import base.model.JobUtil
import base.model.MainRepo
import com.bitplanet.employment.R
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.ResUtil

class PostedJobsRepo : MainRepo(), KoinComponent {

    private val dbInstance by lazy {
        FirebaseFirestore.getInstance()
    }

    private val resUtil: ResUtil by inject()
    private val dataFormatter: DataFormatter by inject()


    fun getJobs(listener: OnGetJobsListener) {
        dbInstance.collection(JobUtil.KEY_JOBS)
                .whereEqualTo(JobUtil.KEY_UID, getLoggedUserTk())
                .get()
                .addOnSuccessListener { documents ->
                    if (documents != null) {
                        listener.onGetJobs(dataFormatter.getJobsFromSnapshot(documents))

                    } else {
                        listener.onErrDb(resUtil.getStringRes(R.string.txt_oops))
                        listener.onShowNoItems()
                    }
                }
                .addOnFailureListener { exception ->
                    listener.onErrDb(exception.message)
                    listener.onShowNoItems()
                }
    }
}