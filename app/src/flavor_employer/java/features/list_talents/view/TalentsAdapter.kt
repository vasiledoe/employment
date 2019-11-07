package features.list_talents.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import base.model.PrettyFormattedTalent
import com.bitplanet.employment.R
import kotlinx.android.synthetic.flavor_employer.custom_talent_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.DataFormatter

class TalentsAdapter(
    private val items: ArrayList<PrettyFormattedTalent>,
    private val clieckedItmListener: (PrettyFormattedTalent) -> Unit
) : RecyclerView.Adapter<TalentsAdapter.ViewHolder>(), KoinComponent {

    private val dataFormatter: DataFormatter by inject()


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_talent_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val talent = items.get(position)

        holder.tvTitle.text = talent.title
        holder.tvField.text = talent.field
        holder.tvDescr.text = talent.descr
        holder.tvPrice.text = talent.price
        holder.tvSeen.text = talent.seen

        holder.wholeZone.setOnClickListener { clieckedItmListener(talent) }

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
        val tvSeen: TextView = view.tv_seen_amount
    }
}