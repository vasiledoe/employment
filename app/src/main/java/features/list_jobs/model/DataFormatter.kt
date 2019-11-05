package features.list_jobs.model

import android.text.format.DateFormat
import base.model.Job
import com.bitplanet.employment.R
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.ResUtil
import java.util.*

class DataFormatter : KoinComponent {

    val resUtil: ResUtil by inject()


    fun getJobsFromSnapshot(documents: QuerySnapshot): ArrayList<PostedJob> {
        val jobs = ArrayList<PostedJob>()
        val gson = Gson()

        for (document in documents) {
            val job = gson.fromJson(gson.toJson(document.data), Job::class.java)

            jobs.add(
                PostedJob(
                    id = document.id,
                    title = job.title,
                    field = getFieldById(job.field),
                    descr = job.descr,
                    address = job.address,
                    price = job.price,
                    time = getPrettyDateFromMilis(job.time),
                    seen = job.seen.toString()
                )
            )
        }

        return jobs
    }

    fun getFieldById(id: Int): String {
        try {
            return resUtil.getStringArrayRes(R.array.fields)[id]

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return resUtil.getStringRes(R.string.txt_unknown)
    }

    fun getPrettyDateFromMilis(neededTimeMilis: Long): String {
        val nowTime = Calendar.getInstance()
        val neededTime = Calendar.getInstance()
        neededTime.timeInMillis = neededTimeMilis

        return if (neededTime.get(Calendar.YEAR) == nowTime.get(Calendar.YEAR)) {

            if (neededTime.get(Calendar.MONTH) == nowTime.get(Calendar.MONTH)) {

                if (neededTime.get(Calendar.DATE) - nowTime.get(Calendar.DATE) == 1) {
                    //here return like "Tomorrow at 12:00"
                    resUtil.getStringRes(R.string.txt_tomorrow) + " " + DateFormat.format(
                        "HH:mm",
                        neededTime
                    )

                } else if (nowTime.get(Calendar.DATE) == neededTime.get(Calendar.DATE)) {
                    //here return like "Today at 12:00"
                    resUtil.getStringRes(R.string.txt_today) + " " + DateFormat.format(
                        "HH:mm",
                        neededTime
                    )

                } else if (nowTime.get(Calendar.DATE) - neededTime.get(Calendar.DATE) == 1) {
                    //here return like "Yesterday at 12:00"
                    resUtil.getStringRes(R.string.txt_ysterday) + " " + DateFormat.format(
                        "HH:mm",
                        neededTime
                    )

                } else {
                    //here return like "May 31, 12:00"
                    DateFormat.format("MMMM d, HH:mm", neededTime).toString()
                }

            } else {
                //here return like "May 31, 12:00"
                DateFormat.format("MMMM d, HH:mm", neededTime).toString()
            }

        } else {
            //here return like "May 31 2010, 12:00" - it's a different year we need to show it
            DateFormat.format("MMMM dd yyyy, HH:mm", neededTime).toString()
        }
    }

}