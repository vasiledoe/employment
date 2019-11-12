package base.view_model

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import base.model.Talent
import com.bitplanet.employment.R
import features.details_job.model.DetailsJobRepo
import features.edit_talent_account.model.EditTalentRepo
import org.koin.core.inject

class FlavorViewModel : BaseViewModel() {

    val isTalentEditedSuccess = MutableLiveData<Boolean>()
    val myTalent = MutableLiveData<Talent>()

    private val editTalentRepo: EditTalentRepo by inject()
    private val detailsJobRepo: DetailsJobRepo by inject()


    fun isTalentDataInserted(talent: Talent): Boolean {
        if (talent.field == 0) {
            error.value = resUtil.getStringRes(R.string.txt_select_field_warn)
            return false
        }

        if (talent.title.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.txt_title_warn)
            return false
        }

        if (talent.descr.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.txt_descr_warn)
            return false
        }

        if (talent.exp.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.txt_exp_warn)
            return false
        }

        if (talent.address.isEmpty()) {
            error.value = resUtil.getStringRes(R.string.txt_adres_warn)
            return false
        }

        if (talent.phone.isNotEmpty()) {
            if (!Patterns.PHONE.matcher(talent.phone).matches()) {
                error.value = resUtil.getStringRes(R.string.txt_phone_warn)
                return false
            }
        }

        if (talent.price == 0) {
            error.value = resUtil.getStringRes(R.string.txt_price_warn)
            return false
        }

        return true
    }

    fun saveUpdateTalent(talent: Talent) {
        isProgressLoading.value = true

        editTalentRepo.insertTalent(
            talent = talent,
            successListener = {
                isProgressLoading.value = false
                isTalentEditedSuccess.value = true
            },
            errListener = { err ->
                isProgressLoading.value = false
                error.value = err ?: getDefErr()
            })
    }

    fun getMyTalent() {
        isProgressLoading.value = true

        editTalentRepo.getMyTalent(
            receivedTalentListener = {
                isProgressLoading.value = false
                myTalent.value = it
            },
            noTalentYetListener = {
                isProgressLoading.value = false
                myTalent.value = null
            },
            errListener = { err ->
                isProgressLoading.value = false
                error.value = err ?: getDefErr()
            }
        )
    }

    fun getJobs(fieldId: Int) {
        isProgressLoading.value = true

        listJobsRepo.getJobs(
            fieldIds = fieldId,
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

    fun doIncrementSeenCoounter(docId: String?) {
        detailsJobRepo.incrementSeenCounter(docId)
    }
}