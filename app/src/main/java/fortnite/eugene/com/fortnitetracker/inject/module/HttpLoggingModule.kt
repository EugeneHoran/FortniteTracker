package fortnite.eugene.com.fortnitetracker.inject.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import fortnite.eugene.com.fortnitetracker.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

@Module
object HttpLoggingModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun httpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY else
                HttpLoggingInterceptor.Level.NONE
        )
    }
}