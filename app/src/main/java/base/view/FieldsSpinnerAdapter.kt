package base.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bitplanet.employment.R
import org.koin.core.KoinComponent
import org.koin.core.inject
import utils.ResUtil

class FieldsSpinnerAdapter(private val mData: Array<String>) :
    BaseSpinnerAdapter(), KoinComponent {

    private var mSelectedPos: Int = 0

    private val resUtil: ResUtil by inject()


    fun setNewSelectedLang(newPos: Int) {
        this.mSelectedPos = newPos
    }

    override fun getItem(position: Int): String {
        return mData[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return getDoneSetup(position, convertView, parent, false)
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return getDoneSetup(position, convertView, parent, true)
    }


    private fun getDoneSetup(
        position: Int, v: View?,
        viewGroup: ViewGroup?, isDropDownItem: Boolean
    ): View? {
        var view = v
        if (view == null)
            view = LayoutInflater.from(viewGroup?.context)
                .inflate(R.layout.custom_spinner_itm, viewGroup, false)

        val rlWholeItem = view?.findViewById<LinearLayout>(R.id.whole_zone)
        val tvText = view?.findViewById<TextView>(R.id.tv_text)

        val curText = mData[position]
        tvText?.text = curText

        if (isDropDownItem) {
            if (position == mSelectedPos) {
                rlWholeItem?.setBackgroundColor(resUtil.getColorRes(R.color.primary_dark_50))

            } else {
                rlWholeItem?.setBackgroundColor(resUtil.getColorRes(R.color.white))
            }
        }
        return view
    }
}