package features.details_job.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import base.view.BaseFrg
import base.view.CustomDialogs
import base.viewModel.BaseViewModel
import com.bitplanet.employment.R
import features.list_jobs.model.PostedJob
import kotlinx.android.synthetic.flavor_employer.frg_details.*

class DetailsFrg : BaseFrg() {

    companion object {
        private val JOB_OBJ_PARCEL = "JOB_OBJ_PARCEL"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param postedJob as PostedJob.
         * @return A new instance of fragment DetailsFrg.
         */
        fun newInstance(postedJob: PostedJob): DetailsFrg {
            val fragment = DetailsFrg()
            val args = Bundle()
            args.putParcelable(JOB_OBJ_PARCEL, postedJob)
            fragment.arguments = args
            return fragment
        }
    }

    private var postedJob: PostedJob? = null

    private lateinit var mViewModel: BaseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            postedJob = arguments?.getParcelable(JOB_OBJ_PARCEL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.frg_details, container, false)

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
            mViewModel = ViewModelProviders.of(it).get(BaseViewModel::class.java)
        }
    }


    private fun setupViews(view: View) {
        val btnRemove: Button = view.findViewById(R.id.tv_remove)
        btnRemove.setOnClickListener { askDeleteJob() }
    }

    private fun loadViews() {
        postedJob.let {
            tv_title.text = it?.title
            tv_field.text = it?.field
            tv_descr.text = it?.descr
            tv_adress.text = it?.address
            tv_time.text = it?.time
            tv_price.text = it?.price
            tv_seen.text = it?.seen.plus(" ").plus(resUtil.getStringRes(R.string.txt_talents))
        }
    }


    private fun askDeleteJob() {
        CustomDialogs().showSimpleDialog(
            activityCtx = activity,
            title = resUtil.getStringRes(R.string.txt_caution),
            msg = resUtil.getStringRes(R.string.txt_ask_delete_job),
            haveCancel = true,
            callbackYes = { postedJob?.id?.let { mViewModel.deleteJob(it) } }
        )
    }
}