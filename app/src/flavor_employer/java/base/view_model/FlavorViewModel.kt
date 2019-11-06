package base.view_model

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import base.model.Job
import com.bitplanet.employment.R
import features.create_job.model.CreateJobRepo
import features.details_job.model.DetailsJobRepo
import org.koin.core.inject

class FlavorViewModel : BaseViewModel() {

    val isJobPostedSuccess = MutableLiveData<Boolean>()
    val isJobDeletedSuccess = MutableLiveData<Boolean>()

    private val createJobRepo: CreateJobRepo by inject()
    private val detailsJobRepo: DetailsJobRepo by inject()


    fun isJobDataInserted(job: Job): Boolean {
        if (job.field == 0) {
            error.value = resUtil.getStringRes(R.string.txt_select_field_warn)
            return false
        }

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

        if (!job.phone.isEmpty()) {
            if (!Patterns.PHONE.matcher(job.phone).matches()) {
                error.value = resUtil.getStringRes(R.string.txt_phone_warn)
                return false
            }
        }

        if (job.price == 0) {
            error.value = resUtil.getStringRes(R.string.txt_price_warn)
            return false
        }

        return true
    }

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
            receivedIJobsListener = { receivedJobs ->
                isProgressLoading.value = false
                jobs.value = receivedJobs
            },
            noItemsListener = {
                isProgressLoading.value = false
                jobs.value = null
            },
            errListener = { err ->
                isProgressLoading.value = false
                error.value = err ?: getDefErr()
            })
    }

}