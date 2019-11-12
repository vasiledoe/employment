package features.details_job.view

import android.content.Intent
import android.net.Uri
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
import base.view_model.FlavorViewModel
import com.bitplanet.employment.R
import kotlinx.android.synthetic.main.frg_details.*

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
            mViewModel = ViewModelProviders.of(it).get(FlavorViewModel::class.java)
        }
    }


    private fun setupViews(view: View) {
        val btnCall: Button = view.findViewById(R.id.btn_call)
        btnCall.setOnClickListener { openCall() }
        val btnEmail: Button = view.findViewById(R.id.btn_email)
        btnEmail.setOnClickListener { openEmail() }
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

            it.phone?.let {
                btn_call.visibility = View.VISIBLE
            }

            mViewModel.doIncrementSeenCoounter(it.id)
        }
    }

    private fun openEmail() {
        val email = prettyFormattedJob?.email

        try {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", email, null)
            )

            startActivity(emailIntent)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openCall() {
        val phone = prettyFormattedJob?.phone

        try {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("tel:$phone")
            startActivity(callIntent)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}