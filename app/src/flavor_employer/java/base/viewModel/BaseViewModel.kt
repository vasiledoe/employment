package base.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import base.model.Job
import base.model.MainRepo
import com.bitplanet.employment.R
import features.create_job.model.CreateJobRepo
import features.details_job.model.DetailsJobRepo
import features.list_jobs.model.PostedJob
import features.list_jobs.model.ListJobsRepo
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.ResUtil

open class BaseViewModel : ViewModel(), KoinComponent {

    val toolbarTitle = MutableLiveData<String>()
    val loggedEmail = MutableLiveData<String>()
    val myJobs = MutableLiveData<ArrayList<PostedJob>>()
    val isJobPostedSuccess = MutableLiveData<Boolean>()
    val isJobDeletedSuccess = MutableLiveData<Boolean>()
    val postedJobDetails = MutableLiveData<PostedJob>()

    val error = MutableLiveData<String>()
    val isProgressLoading = MutableLiveData<Boolean>()
    val goToFrgId = MutableLiveData<Int>()


    private val resUtil: ResUtil by inject()
    private val createJobRepo: CreateJobRepo by inject()
    private val detailsJobRepo: DetailsJobRepo by inject()
    private val mainRepo: MainRepo by inject()
    private val listJobsRepo: ListJobsRepo by inject()


    fun setToolbarTitle(title: String) {
        toolbarTitle.value = title
    }

    fun showLoggeUserEmail() {
        loggedEmail.value = mainRepo.getLoggedUserEmail()
    }

    fun goToFrg(extraId: Int) {
        goToFrgId.value = extraId
    }

    fun goToJobDetails(postedJob: PostedJob) {
        postedJobDetails.value = postedJob
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

    fun getDefErr() = resUtil.getStringRes(R.string.txt_oops)


    fun insertJob(job: Job) {
        isProgressLoading.value = true

        createJobRepo.insertJob(
            job = job,
            successListener = {
                isProgressLoading.value = false
                isJobPostedSuccess.value = true
            },
            errListener = { err ->
                isProgressLoading.value = false
                error.value = err ?: getDefErr()
            })
    }

    fun deleteJob(id: String) {
        isProgressLoading.value = true

        detailsJobRepo.deleteJob(
            id = id,
            successListener = {
                isProgressLoading.value = false
                isJobDeletedSuccess.value = true
            },
            errListener = { err ->
                isProgressLoading.value = false
                error.value = err ?: getDefErr()
            })
    }

    fun getMyPostedJobs() {
        isProgressLoading.value = true

        listJobsRepo.getJobs(
            receivedIJobsListener = { jobs ->
                isProgressLoading.value = false
                myJobs.value = jobs
            },
            noItemsListener = {
                isProgressLoading.value = false
                myJobs.value = null
            },
            errListener = { err ->
                isProgressLoading.value = false
                error.value = err ?: getDefErr()
            })
    }
}