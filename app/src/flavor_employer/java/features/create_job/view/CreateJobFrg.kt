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
import base.viewModel.BaseViewModel
import com.bitplanet.employment.R
import kotlinx.android.synthetic.flavor_employer.frg_add_job.*

class CreateJobFrg : BaseFrg(), View.OnClickListener {

    private lateinit var mViewModel: BaseViewModel
    private var mSelectedFieldId: Int = 0


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.frg_add_job, container, false)

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
            mViewModel = ViewModelProviders.of(it).get(BaseViewModel::class.java)
        }
    }

    private fun setupViews(view: View) {
        val btnAddNewJob: Button = view.findViewById(R.id.btn_create_job)
        btnAddNewJob.setOnClickListener(this)

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
        val fieldsSpinner: Spinner = view.findViewById(R.id.spinner_area)
        val adapter = FieldsSpinnerAdapter(resUtil.getStringArrayRes(R.array.fields))

        fieldsSpinner.adapter = adapter
        fieldsSpinner.setSelection(0)

        fieldsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                mSelectedFieldId = position
                adapter.setNewSelectedLang(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

    }

    private fun tryCreateJob() {
        val job = Job(field = mSelectedFieldId,
                title = et_title.text.toString(),
                descr = et_descr.text.toString(),

                address = if (cb_remote.isChecked) {
                    resUtil.getStringRes(R.string.txt_remot)
                } else {
                    et_adress.text.toString()
                },

                price = if (cb_negociable.isChecked) {
                    resUtil.getStringRes(R.string.txt_negociab)
                } else {
                    et_price.text.toString() + " Mdl"
                })

        if (mViewModel.isJobDataInserted(job)) {
            mViewModel.insertJob(job)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_create_job -> {
                tryCreateJob()
            }
        }
    }
}