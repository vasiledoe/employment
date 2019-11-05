package base.model

/**
 * used to intercept row data from server
 */
data class Job(
    var field: Int,
    var title: String,
    var descr: String,
    var address: String,
    var phone: String = "",
    var price: String,
    var emai: String = "",
    var time: Long = 0,
    var seen: Int = 0
)