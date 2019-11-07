package features.details_talent.model

import base.model.MainRepo
import base.model.TalentUtil
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class DetailsTalentRepo : MainRepo() {

    private val dbInstance by lazy {
        FirebaseFirestore.getInstance()
    }

    fun incrementSeenCounter(docId: String?) {
        docId?.let {
            dbInstance.collection(TalentUtil.KEY_TALENTS)
                .document(it)
                .update(TalentUtil.KEY_SEEN, FieldValue.increment(1))
        }
    }
}