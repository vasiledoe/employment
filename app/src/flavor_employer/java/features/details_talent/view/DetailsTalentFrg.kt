package features.details_talent.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import base.model.PrettyFormattedTalent
import base.view.BaseFrg
import base.view_model.FlavorViewModel
import com.bitplanet.employment.R
import features.details_job.view.DetailsItemsAdapter
import kotlinx.android.synthetic.main.frg_details.*

class DetailsTalentFrg : BaseFrg() {

    companion object {
        private const val KEY_TALENT_OBJ_PARCEL = "KEY_TALENT_OBJ_PARCEL"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param prettyFormattedTalent as PrettyFormattedTalent.
         * @return A new instance of fragment DetailsFrg.
         */
        fun newInstance(prettyFormattedTalent: PrettyFormattedTalent): DetailsTalentFrg {
            val fragment = DetailsTalentFrg()
            val args = Bundle()
            args.putParcelable(KEY_TALENT_OBJ_PARCEL, prettyFormattedTalent)
            fragment.arguments = args
            return fragment
        }
    }

    private var prettyFormattedTalent: PrettyFormattedTalent? = null

    private lateinit var mViewModel: FlavorViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            prettyFormattedTalent = it.getParcelable(KEY_TALENT_OBJ_PARCEL)
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

        mViewModel.setToolbarTitle(resUtil.getStringRes(R.string.txt_talent_det))
    }


    private fun onBindModel() {
        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(FlavorViewModel::class.java)
        }
    }


    private fun setupViews(view: View) {
        val btnCall: Button = view.findViewById(R.id.btn_call)
        btnCall.setOnClickListener { openCall(prettyFormattedTalent?.phone) }
        val btnEmail: Button = view.findViewById(R.id.btn_email)
        btnEmail.setOnClickListener { openEmail(prettyFormattedTalent?.email) }
    }

    private fun loadViews() {
        prettyFormattedTalent?.let {
            rv_itms.layoutManager = LinearLayoutManager(
                activity,
                RecyclerView.VERTICAL,
                false
            )

            rv_itms.adapter = DetailsItemsAdapter(
                items = formatter.getTalentDetailItems(it)
            )

            it.phone?.let {
                btn_call.visibility = View.VISIBLE
            }

            mViewModel.doIncrementSeenCoounter(it.id)
        }
    }
}