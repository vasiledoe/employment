package features.create_job.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import base.BaseFrg
import base.viewModel.BaseViewModel
import com.bitplanet.employment.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreateJobFrg : BaseFrg(), View.OnClickListener {

    private lateinit var mViewModel: BaseViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.frg_add_job, container, false)

        setupViews(view)

        onBindModel()

        return view
    }

    private fun setupViews(view: View) {
        val btnAddNewJob: FloatingActionButton = view.findViewById(R.id.btn_add)
        btnAddNewJob.setOnClickListener(this)

        mViewModel.setToolbarTitle(resUtil.getStringRes(R.string.txt_add))
    }

    private fun onBindModel() {
        mViewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_create_job -> {

            }
        }
    }

}