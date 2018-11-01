package fortnite.eugene.com.fortnitetracker.base

import androidx.lifecycle.ViewModel
import fortnite.eugene.com.fortnitetracker.inject.component.DaggerViewModelInjector
import fortnite.eugene.com.fortnitetracker.inject.component.ViewModelInjector
import fortnite.eugene.com.fortnitetracker.inject.module.FortniteTrackerNetworkModule
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(FortniteTrackerNetworkModule)
        .build()

//    private val subscription: Disposable? = null

    init {
        inject()
    }

    override fun onCleared() {
        super.onCleared()
//        subscription?.dispose()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is LoginViewModel -> injector.inject(this)
        }
    }
}