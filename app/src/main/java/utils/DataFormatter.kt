package utils

import android.text.format.DateFormat
import base.model.Job
import base.model.PrettyFormattedJob
import base.model.Talent
import com.bitplanet.employment.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import features.details_job.model.DetailsItem
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class DataFormatter : KoinComponent {

    val resUtil: ResUtil by inject()

    fun safeParseStrToInt(str: String): Int {
        try {
            return str.toInt()

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return 0
    }


    fun getJobsFromSnapshot(documents: QuerySnapshot): ArrayList<PrettyFormattedJob> {
        val jobs = ArrayList<PrettyFormattedJob>()
        val gson = Gson()

        for (document in documents) {
            val convertedJobObj = getJobFromJson(gson.toJson(document.data), gson)

            convertedJobObj?.let { job ->
                jobs.add(
                    PrettyFormattedJob(
                        id = document.id,
                        title = job.title,
                        field = getFieldById(job.field),
                        descr = job.descr,
                        address = job.address,
                        email = job.email,
                        phone = job.phone,
                        price = if (job.price == -1) {
                            resUtil.getStringRes(R.string.txt_negociab)
                        } else {
                            getPretyFormattedPrice(job.price)
                        },
                        time = getPrettyDateFromMilis(job.time),
                        seen = getPrettyPrintVal(job.seen)
                    )
                )
            }
        }

        return jobs
    }

    fun getJobFromJson(json: String, gson: Gson): Job? {
        try {
            return gson.fromJson(json, Job::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getJobDetailItems(prettyFormattedJob: PrettyFormattedJob): ArrayList<DetailsItem> {
        val list = ArrayList<DetailsItem>()

        list.add(DetailsItem(resUtil.getStringRes(R.string.txt_title), prettyFormattedJob.title))
        list.add(DetailsItem(resUtil.getStringRes(R.string.txt_field), prettyFormattedJob.field))
        list.add(DetailsItem(resUtil.getStringRes(R.string.txt_descr), prettyFormattedJob.descr))
        list.add(DetailsItem(resUtil.getStringRes(R.string.txt_adres), prettyFormattedJob.address))
        list.add(DetailsItem(resUtil.getStringRes(R.string.txt_date), prettyFormattedJob.time))
        list.add(DetailsItem(resUtil.getStringRes(R.string.txt_price), prettyFormattedJob.price))
        list.add(
            DetailsItem(
                resUtil.getStringRes(R.string.txt_seen),
                prettyFormattedJob.seen.plus(" ").plus(resUtil.getStringRes(R.string.txt_talents))
            )
        )
        list.add(DetailsItem(resUtil.getStringRes(R.string.txt_email), prettyFormattedJob.email))
        list.add(
            DetailsItem(
                resUtil.getStringRes(R.string.txt_phone_only),
                prettyFormattedJob.phone
            )
        )

        return list
    }


    fun getTalentFromSnapshot(document: DocumentSnapshot?): Talent? {
        val gson = Gson()

        document?.let { snapshot ->
          return getTalentFromJson(gson.toJson(snapshot.data), gson)
        }

        return null
    }

    fun getTalentFromJson(json: String, gson: Gson): Talent? {
        try {
            return gson.fromJson(json, Talent::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
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

    fun getPretyFormattedPrice(input: Int): String {
        return getPrettyPrintVal(input) + " MDL"
    }

    fun getPrettyPrintVal(input: Int): String {
        val decimalFormat =
            DecimalFormat("###,##0", DecimalFormatSymbols.getInstance(Locale.GERMANY))
        return decimalFormat.format(input)
    }
}