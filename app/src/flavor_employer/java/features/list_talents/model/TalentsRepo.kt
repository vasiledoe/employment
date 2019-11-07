package features.list_talents.model

import base.model.PrettyFormattedTalent
import base.model.TalentUtil
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.DataFormatter

class TalentsRepo : KoinComponent {

    private val dbInstance by lazy {
        FirebaseFirestore.getInstance()
    }

    private val dataFormatter: DataFormatter by inject()


    fun getTalents(
        fieldId: Int,
        receivedTalensListener: (talents: ArrayList<PrettyFormattedTalent>) -> Unit,
        noItemsListener: () -> Unit,
        errListener: (err: String?) -> Unit
    ) {
        val query: Task<QuerySnapshot> = if (fieldId == 0) {
            dbInstance.collection(TalentUtil.KEY_TALENTS)
                .get()

        } else {
            dbInstance.collection(TalentUtil.KEY_TALENTS)
                .whereEqualTo(TalentUtil.KEY_FIELD, fieldId)
                .get()
        }

        query.addOnSuccessListener { documents ->
            if (documents != null) {
                receivedTalensListener(dataFormatter.getTalentsFromSnapshot(documents))

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