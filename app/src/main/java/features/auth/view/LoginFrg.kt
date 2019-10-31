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

class LoginFrg : BaseFrg(), View.OnClickListener {

    private lateinit var mViewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBindModel()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.frg_login, container, false)

        setupViews(view)

        return view
    }

    private fun setupViews(view: View) {
        val btnLogin = view.findViewById<View>(R.id.btn_login)
        btnLogin.setOnClickListener(this)

        val txtForgot = view.findViewById<View>(R.id.tv_forgot_pass)
        txtForgot.setOnClickListener(this)

        val txtCreate = view.findViewById<View>(R.id.tv_reg)
        txtCreate.setOnClickListener(this)
    }


    private fun onBindModel() {
        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(AuthViewModel::class.java)
        }
    }

    private fun tryLogin() {
        if (mViewModel.isLoginDataInserted(
                        email = et_email.text.toString(),
                        pass = et_pass.text.toString())) {

            mViewModel.setLoading(true)

            mViewModel.doLogin(
                    email = et_email.text.toString(),
                    pass = et_pass.text.toString()
            )
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                tryLogin()
            }

            R.id.tv_forgot_pass -> {
                mViewModel.goToFrg(AuthActivity.RECOVERY)
            }

            R.id.tv_reg -> {
                mViewModel.goToFrg(AuthActivity.REGISTER)
            }
        }
    }
}