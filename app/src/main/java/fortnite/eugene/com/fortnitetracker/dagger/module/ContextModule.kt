package fortnite.eugene.com.fortnitetracker.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import fortnite.eugene.com.fortnitetracker.dagger.AppScope

@Module
class ContextModule(val context: Context) {
    @Provides
    @AppScope
    fun context(): Context {
        return context
    }
}