package utils

import android.util.Log

class MyLogs {

    fun LOG(theClass: String, theMethod: String, theComment: String) {
        Log.d("!!! MyLogs !!!", "class: $theClass meth : $theMethod comm : $theComment")
    }
}