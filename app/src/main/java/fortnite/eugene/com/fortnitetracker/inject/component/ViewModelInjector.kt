package fortnite.eugene.com.fortnitetracker.inject.component

import dagger.Component
import fortnite.eugene.com.fortnitetracker.inject.module.FortniteTrackerNetworkModule
import fortnite.eugene.com.fortnitetracker.inject.module.HttpLoggingModule
import fortnite.eugene.com.fortnitetracker.ui.account.matchhistory.MatchHistoryViewModel
import fortnite.eugene.com.fortnitetracker.ui.challenges.ChallengesViewModel
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.ui.store.StoreViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HttpLoggingModule::class,
        FortniteTrackerNetworkModule::class
    ]
)
interface ViewModelInjector {
    fun inject(viewModel: LoginViewModel)
    fun inject(viewModel: MatchHistoryViewModel)
    fun inject(viewModel: ChallengesViewModel)
    fun inject(viewModel: StoreViewModel)
    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(fortniteTrackerNetworkModule: FortniteTrackerNetworkModule): Builder
    }
}