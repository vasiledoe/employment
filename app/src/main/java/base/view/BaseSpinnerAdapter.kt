package base.view

import android.database.DataSetObserver
import android.widget.SpinnerAdapter

open abstract class BaseSpinnerAdapter : SpinnerAdapter {

    override fun registerDataSetObserver(dataSetObserver: DataSetObserver) {}

    override fun unregisterDataSetObserver(dataSetObserver: DataSetObserver) {}

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getItemViewType(i: Int): Int {
        return 0
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        return false
    }
}