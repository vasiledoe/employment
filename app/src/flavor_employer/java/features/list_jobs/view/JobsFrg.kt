package features.list_jobs.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import base.view.BaseActivity
import base.view.BaseFrg
import base.viewModel.BaseViewModel
import com.bitplanet.employment.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import features.list_jobs.model.PostedJob
import kotlinx.android.synthetic.flavor_employer.frg_jobs.*

class JobsFrg : BaseFrg(), View.OnClickListener {

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mViewModel: BaseViewModel

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
        mViewModel.getMyPostedJobs()
    }


    private fun onBindModel() {
        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(BaseViewModel::class.java)

            mViewModel.myJobs.observe(it, Observer { items ->
                view?.let { loadItems(items) }
            })
        }
    }

    private fun setupViews(view: View) {
        val btnAddNewJob: FloatingActionButton = view.findViewById(R.id.btn_add)
        btnAddNewJob.setOnClickListener(this)

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_zone)
        mSwipeRefreshLayout.setOnRefreshListener { mViewModel.getMyPostedJobs() }
    }

    private fun loadItems(jobs: ArrayList<PostedJob>?) {
        mSwipeRefreshLayout.isRefreshing = false

        if (jobs != null) {
            if (jobs.size > 0) {
                handleItmsVisibility(rv_itms, zone_no_items, HAS_ITEMS)

                rv_itms.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                rv_itms.adapter = JobsAdapter(
                    items = jobs,
                    clieckedItmListener = { mViewModel.goToJobDetails(it) }
                )

            } else {
                handleItmsVisibility(rv_itms, zone_no_items, NO_ITEMS)
            }

        } else {
            handleItmsVisibility(rv_itms, zone_no_items, NO_ITEMS)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add -> {
                mViewModel.goToFrg(BaseActivity.CREATE_JOB_FRG)
            }
        }
    }
}