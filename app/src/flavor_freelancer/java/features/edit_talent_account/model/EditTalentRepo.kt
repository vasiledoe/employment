package features.edit_talent_account.model

import android.util.Log
import base.model.JobUtil
import base.model.MainRepo
import base.model.Talent
import base.model.TalentUtil
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.DataFormatter

class EditTalentRepo : MainRepo(), KoinComponent {

    private val dbInstance by lazy {
        FirebaseFirestore.getInstance()
    }

    private val dataFormatter: DataFormatter by inject()


    fun insertTalent(
        talent: Talent,
        successListener: () -> Unit,
        errListener: (err: String?) -> Unit
    ) {
        val jobMap = hashMapOf(
            TalentUtil.KEY_FIELD to talent.field,
            TalentUtil.KEY_TITLE to talent.title,
            TalentUtil.KEY_DESCR to talent.descr,
            TalentUtil.KEY_ADRESS to talent.address,
            TalentUtil.KEY_EXPERIENCE to talent.exp,
            TalentUtil.KEY_PHONE to talent.phone,
            TalentUtil.KEY_PRICE to talent.price,
            TalentUtil.KEY_TIME to System.currentTimeMillis(),
            TalentUtil.KEY_EMAIL to getLoggedUserEmail(),
            TalentUtil.KEY_SEEN to 0
        )


        val docRef: DocumentReference = dbInstance.collection(TalentUtil.KEY_TALENTS)
            .document(getLoggedUserTk())

        docRef.get()
            .addOnSuccessListener {

                if (it.exists()) {
                    docRef.update(jobMap)
                        .addOnSuccessListener { successListener() }
                        .addOnFailureListener { e -> errListener(e.message) }

                } else {
                    docRef.set(jobMap)
                        .addOnSuccessListener { successListener() }
                        .addOnFailureListener { e -> errListener(e.message) }
                }
            }
            .addOnFailureListener { e -> errListener(e.message) }
    }

    fun getMyTalent(
        receivedTalentListener: (talent: Talent?) -> Unit,
        noTalentYetListener: () -> Unit,
        errListener: (err: String?) -> Unit
    ) {

        dbInstance.collection(TalentUtil.KEY_TALENTS)
            .document(getLoggedUserTk())
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    receivedTalentListener(dataFormatter.getTalentFromSnapshot(document))

                } else {
                    noTalentYetListener()
                }
            }
            .addOnFailureListener { exception ->
                errListener(exception.message)
                noTalentYetListener()
            }
    }
}