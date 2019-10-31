package features.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import base.BaseFrg
import com.bitplanet.employment.R
import features.auth.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.frg_register.*

class RegisterFrg : BaseFrg(), View.OnClickListener {

    private lateinit var mViewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBindModel()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.frg_register, container, false)

        setupViews(view)

        return view
    }

    private fun setupViews(view: View) {
        val btnReg = view.findViewById<View>(R.id.btn_register)
        btnReg.setOnClickListener(this)
    }


    private fun onBindModel() {
        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(AuthViewModel::class.java)
        }
    }

    private fun tryRegister() {
        if (mViewModel.isRegDataInserted(
                        email = et_email.text.toString(),
                        pass = et_pass.text.toString(),
                        passRepeat = et_pass_retype.text.toString())) {

            mViewModel.setLoading(true)

            mViewModel.doCreateAccount(
                    email = et_email.text.toString(),
                    pass = et_pass.text.toString()
            )
        }
    }


    override fun onClick(view: View) {
        tryRegister()
    }
}