package fortnite.eugene.com.fortnitetracker.dagger.component

import dagger.Component
import fortnite.eugene.com.fortnitetracker.dagger.AppScope
import fortnite.eugene.com.fortnitetracker.dagger.module.ApiStatsModule
import fortnite.eugene.com.fortnitetracker.dagger.module.ContextModule
import fortnite.eugene.com.fortnitetracker.dagger.module.FortniteDatabaseModule
import fortnite.eugene.com.fortnitetracker.dagger.module.HttpLoggingInterceptorModule
import fortnite.eugene.com.fortnitetracker.ui.MainActivity
import fortnite.eugene.com.fortnitetracker.ui.login.EpicLoginViewModel
import fortnite.eugene.com.fortnitetracker.ui.stats.StatsViewModel
import javax.inject.Singleton

@Singleton
@AppScope
@Component(
    modules = [
        ContextModule::class,
        HttpLoggingInterceptorModule::class,
        ApiStatsModule::class,
        FortniteDatabaseModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(epicLoginViewModel: EpicLoginViewModel)
    fun inject(statsViewModel: StatsViewModel)

}