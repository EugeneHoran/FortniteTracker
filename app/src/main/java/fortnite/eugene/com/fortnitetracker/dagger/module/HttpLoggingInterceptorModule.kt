package fortnite.eugene.com.fortnitetracker.dagger.module

import dagger.Module
import dagger.Provides
import fortnite.eugene.com.fortnitetracker.BuildConfig
import fortnite.eugene.com.fortnitetracker.dagger.AppScope
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Logging Interceptor to display data in logcat
 */
@Module(includes = [ContextModule::class])
class HttpLoggingInterceptorModule {
    @Provides
    @AppScope
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY else
                HttpLoggingInterceptor.Level.NONE
        )
    }
}