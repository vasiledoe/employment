package base.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent

class BaseViewModel : ViewModel(), KoinComponent {

    val error = MutableLiveData<String>()
    val toolbarTitle = MutableLiveData<String>()
    val goToFrgId = MutableLiveData<Int>()


    fun setToolbarTitle(title: String) {
        toolbarTitle.value = title
    }

    fun goToFrg(extraId: Int) {
        goToFrgId.value = extraId
    }
}