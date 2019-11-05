package features.list_talents.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import base.view.BaseFrg
import com.bitplanet.employment.R

class TalentsFrg : BaseFrg() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.frg_jobs, container, false)

        return view
    }


}