package base.model

/**
 * used to intercept row data from server
 */
data class Job(
        var field: Int,
        var title: String,
        var descr: String,
        var address: String,
        var price: String,
        var time: Long = 0,
        var applicants: Int = 0,
        var status: Int = 0
)