package fortnite.eugene.com.fortnitetracker

import android.app.Application
import fortnite.eugene.com.fortnitetracker.dagger.component.AppComponent
import fortnite.eugene.com.fortnitetracker.dagger.component.DaggerAppComponent
import fortnite.eugene.com.fortnitetracker.dagger.module.ContextModule

class App : Application() {
    companion object {
        @JvmStatic
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }
}