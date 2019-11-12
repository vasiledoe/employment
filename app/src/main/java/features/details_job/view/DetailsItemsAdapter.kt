package features.details_job.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bitplanet.employment.R
import features.details_job.model.DetailsItem
import kotlinx.android.synthetic.main.custom_job_detail_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.ResUtil

class DetailsItemsAdapter(private val items: ArrayList<DetailsItem>) :
    RecyclerView.Adapter<DetailsItemsAdapter.ViewHolder>(), KoinComponent {

    private val resUtil: ResUtil by inject()


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_job_detail_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = items[position]

        holder.tvTitle.text = job.title
        holder.tvDetails.text = job.details


        if (position % 2 == 1) {
            holder.wholeZone.setBackgroundColor(resUtil.getColorRes(R.color.divider_30))

        } else {
            holder.wholeZone.setBackgroundColor(resUtil.getColorRes(R.color.white))
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val wholeZone: LinearLayout = view.whole_item
        val tvTitle: TextView = view.tv_title
        val tvDetails: TextView = view.tv_details
    }
}