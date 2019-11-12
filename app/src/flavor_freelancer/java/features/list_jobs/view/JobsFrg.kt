package features.list_jobs.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import base.model.PrettyFormattedJob
import base.view.BaseFrg
import base.view.FieldsSpinnerAdapter
import base.view_model.FlavorViewModel
import com.bitplanet.employment.R
import kotlinx.android.synthetic.flavor_freelancer.frg_jobs.*

class JobsFrg : BaseFrg() {

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mViewModel: FlavorViewModel

    private var mSelectedFieldId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.frg_jobs, container, false)

        setupViews(view)

        onBindModel()

        return view
    }

    override fun onResume() {
        super.onResume()

        mViewModel.setToolbarTitle(resUtil.getStringRes(R.string.menu_home))
        mViewModel.getJobs(mSelectedFieldId)
    }


    private fun onBindModel() {
        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(FlavorViewModel::class.java)

            mViewModel.jobs.observe(it, Observer { items ->
                view?.let { loadItems(items) }
            })
        }
    }

    private fun setupViews(view: View) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_zone)
        mSwipeRefreshLayout.setOnRefreshListener { mViewModel.getJobs(mSelectedFieldId) }

        loadFields(view)
    }

    private fun loadFields(view: View) {
        val fieldsSpinner: Spinner = view.findViewById(R.id.spinner_fields)
        val adapter = FieldsSpinnerAdapter(resUtil.getStringArrayRes(R.array.fields))

        fieldsSpinner.adapter = adapter
        fieldsSpinner.setSelection(0)

        fieldsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                mSelectedFieldId = position
                adapter.setNewSelectedLang(position)

                mViewModel.getJobs(mSelectedFieldId)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

    }

    private fun loadItems(jobs: ArrayList<PrettyFormattedJob>?) {
        mSwipeRefreshLayout.isRefreshing = false

        if (jobs != null) {
            if (jobs.size > 0) {
                handleItmsVisibility(rv_itms, zone_no_items, HAS_ITEMS)

                rv_itms.layoutManager = LinearLayoutManager(
                    activity,
                    RecyclerView.VERTICAL,
                    false
                )

                rv_itms.adapter = JobsAdapter(
                    items = jobs,
                    clickedItmListener = { mViewModel.goToJobDetails(it) }
                )

            } else {
                handleItmsVisibility(rv_itms, zone_no_items, NO_ITEMS)
            }

        } else {
            handleItmsVisibility(rv_itms, zone_no_items, NO_ITEMS)
        }
    }
}