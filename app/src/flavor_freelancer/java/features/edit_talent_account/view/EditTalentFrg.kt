package features.edit_talent_account.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import base.model.Talent
import base.view.BaseFrg
import base.view.FieldsSpinnerAdapter
import base.view_model.FlavorViewModel
import com.bitplanet.employment.R
import kotlinx.android.synthetic.flavor_freelancer.frg_edit_talent.*

class EditTalentFrg : BaseFrg() {

    private lateinit var mViewModel: FlavorViewModel
    private var mFieldsSpinnerAdapter: FieldsSpinnerAdapter? = null
    private lateinit var mFieldsSpinner: Spinner
    private var mSelectedFieldId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.frg_edit_talent, container, false)

        setupViews(view)

        onBindModel()

        return view
    }

    override fun onResume() {
        super.onResume()

        mViewModel.setToolbarTitle(resUtil.getStringRes(R.string.menu_account))
        mViewModel.getMyTalent()
    }


    private fun onBindModel() {
        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(FlavorViewModel::class.java)

            mViewModel.myTalent.observe(it, Observer { myTalent ->
                myTalent?.let { view?.let { loadData(myTalent) } }
            })
        }
    }

    private fun setupViews(view: View) {
        val btnAddSave: Button = view.findViewById(R.id.btn_save_talent)
        btnAddSave.setOnClickListener { tryEditTalent() }

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
        mFieldsSpinner = view.findViewById(R.id.spinner_fields)
        mFieldsSpinnerAdapter = FieldsSpinnerAdapter(resUtil.getStringArrayRes(R.array.fields))

        mFieldsSpinner.adapter = mFieldsSpinnerAdapter
        mFieldsSpinner.setSelection(0)

        mFieldsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                mSelectedFieldId = position
                mFieldsSpinnerAdapter?.setNewSelectedLang(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }
    }

    private fun loadData(talent: Talent) {
        et_title.setText(talent.title)
        et_descr.setText(talent.descr)
        et_exp.setText(talent.exp)
        et_adress.setText(talent.address)
        et_phone.setText(talent.phone)

        if (talent.price == -1) {
            cb_negociable.isChecked = true
            et_price.isEnabled = false

        } else {
            cb_negociable.isChecked = false
            et_price.isEnabled = true
            et_price.setText(talent.price.toString())
        }

        mFieldsSpinnerAdapter?.let {
            mFieldsSpinner.setSelection(talent.field)
            it.setNewSelectedLang(talent.field)
        }
    }


    private fun tryEditTalent() {
        val talent = Talent(
            field = mSelectedFieldId,
            title = et_title.text.toString(),
            descr = et_descr.text.toString(),
            exp = et_exp.text.toString(),

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

        if (mViewModel.isTalentDataInserted(talent)) {
            mViewModel.saveUpdateTalent(talent)
        }
    }
}