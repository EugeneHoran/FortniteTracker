package fortnite.eugene.com.fortnitetracker.inject.component

import dagger.Component
import fortnite.eugene.com.fortnitetracker.inject.module.FortniteTrackerNetworkModule
import fortnite.eugene.com.fortnitetracker.inject.module.HttpLoggingModule
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HttpLoggingModule::class,
        FortniteTrackerNetworkModule::class
    ]
)
interface ViewModelInjector {
    fun inject(loginViewModel: LoginViewModel)
    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(fortniteTrackerNetworkModule: FortniteTrackerNetworkModule): Builder
    }
}