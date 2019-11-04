package features.posted_jobs.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import base.view.BaseActivity
import base.view.BaseFrg
import base.viewModel.BaseViewModel
import com.bitplanet.employment.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import features.posted_jobs.model.PostedJob
import kotlinx.android.synthetic.flavor_employer.frg_jobs.*

class JobsFrg : BaseFrg(), View.OnClickListener {

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
                loadItems(items)
            })
        }
    }

    private fun setupViews(view: View) {
        val btnAddNewJob: FloatingActionButton = view.findViewById(R.id.btn_add)
        btnAddNewJob.setOnClickListener(this)
    }

    private fun loadItems(jobs: ArrayList<PostedJob>?) {
        if (jobs != null) {
            if (jobs.size > 0) {
                handleItmsVisibility(rv_itms, zone_no_items, HAS_ITEMS)

                rv_itms.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                val adapter = JobsAdapter(
                    jobs, ({ selectedJob -> openAppliants() })
                )//todo de pus id de la doc
                rv_itms.adapter = adapter


            } else {
                handleItmsVisibility(rv_itms, zone_no_items, NO_ITEMS)
            }

        } else {
            handleItmsVisibility(rv_itms, zone_no_items, NO_ITEMS)
        }
    }

    private fun openAppliants() {

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add -> {
                mViewModel.goToFrg(BaseActivity.CREATE_JOB_FRG)
            }
        }
    }
}