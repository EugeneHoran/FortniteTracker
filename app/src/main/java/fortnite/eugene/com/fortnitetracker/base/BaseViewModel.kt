package fortnite.eugene.com.fortnitetracker.base

import androidx.lifecycle.ViewModel
import fortnite.eugene.com.fortnitetracker.inject.component.DaggerViewModelInjector
import fortnite.eugene.com.fortnitetracker.inject.component.ViewModelInjector
import fortnite.eugene.com.fortnitetracker.inject.module.FortniteTrackerNetworkModule
import fortnite.eugene.com.fortnitetracker.ui.account.matchhistory.MatchHistoryViewModel
import fortnite.eugene.com.fortnitetracker.ui.challenges.ChallengesViewModel
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.ui.store.StoreViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(FortniteTrackerNetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is LoginViewModel -> injector.inject(this)
            is MatchHistoryViewModel -> injector.inject(this)
            is ChallengesViewModel -> injector.inject(this)
            is StoreViewModel -> injector.inject(this)
        }
    }
}