package fortnite.eugene.com.fortnitetracker.inject.module

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory
import com.github.leonardoxh.livedatacalladapter.LiveDataResponseBodyConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [HttpLoggingModule::class])
object FortniteTrackerNetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideFortniteTrackerApi(retrofit: Retrofit): FortniteTrackerApi {
        return retrofit.create(FortniteTrackerApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideOkHttpClient(httpLogger: HttpLoggingInterceptor): OkHttpClient {
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
        builder.addInterceptor(httpLogger)
        return builder.build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.fortnitetracker.com/")
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}