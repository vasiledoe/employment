package base.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import base.model.MainRepo
import base.model.PrettyFormattedJob
import base.model.PrettyFormattedTalent
import com.bitplanet.employment.R
import features.list_jobs.model.ListJobsRepo
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.ResUtil

open class BaseViewModel : ViewModel(), KoinComponent {

    val toolbarTitle = MutableLiveData<String>()
    val loggedEmail = MutableLiveData<String>()
    val jobs = MutableLiveData<ArrayList<PrettyFormattedJob>>()
    val jobDetails = MutableLiveData<PrettyFormattedJob>()
    val talentDetails = MutableLiveData<PrettyFormattedTalent>()

    val error = MutableLiveData<String>()
    val isProgressLoading = MutableLiveData<Boolean>()
    val goToFrgId = MutableLiveData<Int>()


    protected val resUtil: ResUtil by inject()
    protected val mainRepo: MainRepo by inject()
    protected val listJobsRepo: ListJobsRepo by inject()


    fun setToolbarTitle(title: String) {
        toolbarTitle.value = title
    }

    fun showLoggeUserEmail() {
        loggedEmail.value = mainRepo.getLoggedUserEmail()
    }

    fun goToFrg(extraId: Int) {
        goToFrgId.value = extraId
    }

    fun goToJobDetails(prettyFormattedJob: PrettyFormattedJob) {
        jobDetails.value = prettyFormattedJob
    }

    fun goToTalentDetails(prettyFormattedJob: PrettyFormattedTalent) {
        talentDetails.value = prettyFormattedJob
    }

    fun getDefErr() = resUtil.getStringRes(R.string.txt_oops)

}