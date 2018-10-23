package fortnite.eugene.com.fortnitetracker.dagger.module

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory
import com.github.leonardoxh.livedatacalladapter.LiveDataResponseBodyConverterFactory
import dagger.Module
import dagger.Provides
import fortnite.eugene.com.fortnitetracker.dagger.AppScope
import fortnite.eugene.com.fortnitetracker.data.service.StatsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module(includes = [HttpLoggingInterceptorModule::class])
class ApiStatsModule {

    @Provides
    @AppScope
    @Named("StatsClient")
    fun okHttpStatsClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val urlBuilder = originalHttpUrl.newBuilder()
            val requestBuilder = original
                .newBuilder()
                .url(urlBuilder.build())
            requestBuilder.addHeader("TRN-Api-Key", "66757414-943b-47ff-b39c-78ad867300ae")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        builder.addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @AppScope
    @Named("RetrofitStats")
    fun retrofitStats(@Named("StatsClient") okHttpStatsClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.fortnitetracker.com/")
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpStatsClient)
            .build()
    }

    @Provides
    @AppScope
    fun statsService(@Named("RetrofitStats") retrofitStats: Retrofit): StatsService {
        return retrofitStats.create(StatsService::class.java)
    }
}