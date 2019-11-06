package features.list_jobs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bitplanet.employment.R
import utils.DataFormatter
import base.model.PrettyFormattedJob
import kotlinx.android.synthetic.main.custom_job_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class JobsAdapter(
    private val items: ArrayList<PrettyFormattedJob>,
    private val clieckedItmListener: (PrettyFormattedJob) -> Unit
) : RecyclerView.Adapter<JobsAdapter.ViewHolder>(), KoinComponent {

    private val dataFormatter: DataFormatter by inject()


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_job_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = items.get(position)

        holder.tvTitle.text = job.title
        holder.tvField.text = job.field
        holder.tvDescr.text = job.descr
        holder.tvPrice.text = job.price
        holder.tvTime.text = job.time
        holder.tvSeen.text = job.seen

        holder.wholeZone.setOnClickListener {clieckedItmListener(job)}

        if (position % 2 == 1) {
            holder.wholeZone.setBackgroundColor(dataFormatter.resUtil.getColorRes(R.color.divider_30))

        } else {
            holder.wholeZone.setBackgroundColor(dataFormatter.resUtil.getColorRes(R.color.white))
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val wholeZone: LinearLayout = view.whole_item
        val tvTitle: TextView = view.tv_title
        val tvField: TextView = view.tv_field
        val tvDescr: TextView = view.tv_descr
        val tvPrice: TextView = view.tv_price
        val tvTime: TextView = view.tv_time
        val tvSeen: TextView = view.tv_seen_amount
    }
}