package base.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import base.model.Job
import base.model.MainRepo
import com.bitplanet.employment.R
import features.create_job.model.CreateJobRepo
import features.create_job.model.OnInsertJobListener
import features.posted_jobs.model.OnGetJobsListener
import features.posted_jobs.model.PostedJob
import features.posted_jobs.model.PostedJobsRepo
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.ResUtil

class BaseViewModel : ViewModel(), OnInsertJobListener, OnGetJobsListener, KoinComponent {

    val toolbarTitle = MutableLiveData<String>()
    val loggedEmail = MutableLiveData<String>()
    val myJobs = MutableLiveData<ArrayList<PostedJob>>()
    val isJobPostedSuccess = MutableLiveData<Boolean>()

    val error = MutableLiveData<String>()
    val isProgressLoading = MutableLiveData<Boolean>()
    val goToFrgId = MutableLiveData<Int>()


    private val resUtil: ResUtil by inject()
    private val createJobRepo: CreateJobRepo by inject()
    private val mainRepo: MainRepo by inject()
    private val postedJobsRepo: PostedJobsRepo by inject()


    fun setToolbarTitle(title: String) {
        toolbarTitle.value = title
    }

    fun goToFrg(extraId: Int) {
        goToFrgId.value = extraId
    }

    fun isJobDataInserted(job: Job): Boolean {
        if (job.title.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.txt_title_warn)
            return false
        }

        if (job.descr.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.txt_descr_warn)
            return false
        }

        if (job.address.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.txt_adres_warn)
            return false
        }

        if (job.price.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.txt_price_warn)
            return false
        }

        return true
    }

    fun insertJob(job: Job) {
        isProgressLoading.value = true

        createJobRepo.insertJob(job, listener = this@BaseViewModel)
    }

    fun showLoggeUserEmail() {
        loggedEmail.value = mainRepo.getLoggedUserEmail()
    }

    fun getMyPostedJobs() {
        isProgressLoading.value = true

        postedJobsRepo.getJobs(listener = this@BaseViewModel)
    }


    override fun onInsertJobSuccess() {
        isProgressLoading.value = false
        isJobPostedSuccess.value = true
    }

    override fun onGetJobs(jobs: ArrayList<PostedJob>) {
        isProgressLoading.value = false
        myJobs.value = jobs
    }

    override fun onShowNoItems() {
        isProgressLoading.value = false
        myJobs.value = null
    }

    override fun onErrDb(err: String?) {
        isProgressLoading.value = false
        error.value = err
    }
}