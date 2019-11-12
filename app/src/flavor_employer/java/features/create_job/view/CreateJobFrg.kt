package features.create_job.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import androidx.lifecycle.ViewModelProviders
import base.model.Job
import base.view.BaseFrg
import base.view.FieldsSpinnerAdapter
import base.view_model.FlavorViewModel
import com.bitplanet.employment.R
import kotlinx.android.synthetic.flavor_employer.frg_create_job.*

class CreateJobFrg : BaseFrg() {

    private lateinit var mViewModel: FlavorViewModel
    private var mSelectedFieldId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.frg_create_job, container, false)

        setupViews(view)

        onBindModel()

        return view
    }

    override fun onResume() {
        super.onResume()

        mViewModel.setToolbarTitle(resUtil.getStringRes(R.string.txt_add))
    }


    private fun onBindModel() {
        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(FlavorViewModel::class.java)
        }
    }

    private fun setupViews(view: View) {
        val btnAddNewJob: Button = view.findViewById(R.id.btn_create_job)
        btnAddNewJob.setOnClickListener { tryCreateJob() }

        val cbRemote: CheckBox = view.findViewById(R.id.cb_remote)
        cbRemote.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                et_adress.setText("")
                et_adress.isEnabled = false

            } else {
                et_adress.isEnabled = true
            }
        }

        val cbNegot: CheckBox = view.findViewById(R.id.cb_negociable)
        cbNegot.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                et_price.setText("")
                et_price.isEnabled = false

            } else {
                et_price.isEnabled = true
            }
        }

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
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

    }

    private fun tryCreateJob() {
        val job = Job(
            field = mSelectedFieldId,
            title = et_title.text.toString(),
            descr = et_descr.text.toString(),

            address = if (cb_remote.isChecked) {
                resUtil.getStringRes(R.string.txt_remot)
            } else {
                et_adress.text.toString()
            },

            phone = et_phone.text.toString(),

            price = if (cb_negociable.isChecked) {
                -1
            } else {
                formatter.safeParseStrToInt(et_price.text.toString())
            }
        )

        if (mViewModel.isJobDataInserted(job)) {
            mViewModel.insertJob(job)
        }
    }

}