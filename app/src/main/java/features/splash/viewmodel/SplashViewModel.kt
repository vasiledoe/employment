package features.splash.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import features.splash.model.SplashRepo
import org.koin.core.KoinComponent
import org.koin.core.inject

class SplashViewModel : ViewModel(), KoinComponent {

    val isLogged = MutableLiveData<Boolean>()

    private val splashRepo: SplashRepo by inject()


    fun doSilentSignIn() {
        splashRepo.silentSignIn(signedInListener = { isSignedIn -> isLogged.value = isSignedIn })
    }
}