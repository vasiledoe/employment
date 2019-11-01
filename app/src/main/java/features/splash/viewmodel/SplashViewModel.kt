package features.splash.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import features.splash.model.OnSilentAuthListener
import features.splash.model.SplashRepo
import org.koin.core.KoinComponent
import org.koin.core.inject

class SplashViewModel : ViewModel(), KoinComponent, OnSilentAuthListener {

    val isLogged = MutableLiveData<Boolean>()


    private val splashRepo: SplashRepo by inject()


    fun doSilentSignIn() {
        splashRepo.silentSignIn(listener = this@SplashViewModel)
    }

    override fun onSilentAuth(status: Boolean) {
        isLogged.value = status
    }
}