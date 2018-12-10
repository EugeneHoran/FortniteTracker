package fortnite.eugene.com.fortnitetracker.base

import androidx.lifecycle.ViewModel
import fortnite.eugene.com.fortnitetracker.inject.component.DaggerViewModelInjector
import fortnite.eugene.com.fortnitetracker.inject.component.ViewModelInjector
import fortnite.eugene.com.fortnitetracker.inject.module.FortniteTrackerNetworkModule
import fortnite.eugene.com.fortnitetracker.ui.challenges.ChallengesViewModel
import fortnite.eugene.com.fortnitetracker.ui.history.MatchHistoryViewModel
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.ui.store.StoreViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var navigator: WeakReference<N>? = null

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(FortniteTrackerNetworkModule)
        .build()

    init {
        inject()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
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

    fun getCompositeDisposable(): CompositeDisposable = compositeDisposable

    fun getNavigator(): N? {
        return navigator?.get()
    }

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun clearNavigator() {
        if (navigator != null) {
            navigator = null
        }
    }
}