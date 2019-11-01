package features.posted_jobs.model

/**
 * used for ready to show data beans in adapter
 */
data class PostedJob (
        var id: String,
        var field: String,
        var title: String,
        var descr: String,
        var address: String,
        var price: String,
        var time: String,
        var applicants: String,
        var status: String
)