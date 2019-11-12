package features.details_job.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import base.model.PrettyFormattedJob
import base.view.BaseFrg
import base.view.CustomDialogs
import base.view_model.FlavorViewModel
import com.bitplanet.employment.R
import kotlinx.android.synthetic.flavor_employer.frg_job_details.*

class DetailsFrg : BaseFrg() {

    companion object {
        private const val KEY_JOB_OBJ_PARCEL = "KEY_JOB_OBJ_PARCEL"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param prettyFormattedJob as PrettyFormattedJob.
         * @return A new instance of fragment DetailsFrg.
         */
        fun newInstance(prettyFormattedJob: PrettyFormattedJob): DetailsFrg {
            val fragment = DetailsFrg()
            val args = Bundle()
            args.putParcelable(KEY_JOB_OBJ_PARCEL, prettyFormattedJob)
            fragment.arguments = args
            return fragment
        }
    }

    private var prettyFormattedJob: PrettyFormattedJob? = null

    private lateinit var mViewModel: FlavorViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            prettyFormattedJob = it.getParcelable(KEY_JOB_OBJ_PARCEL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.frg_job_details, container, false)

        setupViews(view)

        onBindModel()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadViews()
    }

    override fun onResume() {
        super.onResume()

        mViewModel.setToolbarTitle(resUtil.getStringRes(R.string.txt_job_det))
    }


    private fun onBindModel() {
        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(FlavorViewModel::class.java)
        }
    }


    private fun setupViews(view: View) {
        val btnRemove: Button = view.findViewById(R.id.btn_remove_job)
        btnRemove.setOnClickListener { askDeleteJob() }
    }

    private fun loadViews() {
        prettyFormattedJob?.let {
            rv_itms.layoutManager = LinearLayoutManager(
                activity,
                RecyclerView.VERTICAL,
                false
            )
            rv_itms.adapter = DetailsItemsAdapter(
                items = formatter.getJobDetailItems(it)
            )
        }
    }


    private fun askDeleteJob() {
        CustomDialogs().showSimpleDialog(
            activityCtx = activity,
            title = resUtil.getStringRes(R.string.txt_caution),
            msg = resUtil.getStringRes(R.string.txt_ask_delete_job),
            haveCancel = true,
            callbackYes = { prettyFormattedJob?.id?.let { mViewModel.deleteJob(it) } }
        )
    }
}