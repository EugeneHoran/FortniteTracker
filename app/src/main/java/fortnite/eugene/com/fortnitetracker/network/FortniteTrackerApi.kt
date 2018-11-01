package fortnite.eugene.com.fortnitetracker.network

import androidx.lifecycle.LiveData
import com.github.leonardoxh.livedatacalladapter.Resource
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface FortniteTrackerApi {
    @GET("v1/profile/{platform}/{epic_nickname}")
    fun getUserStats(
        @Path("platform") platform: String,
        @Path("epic_nickname") epic_nickname: String
    ): LiveData<Resource<AccountStats>>

    // TODO Implement RxJava and LiveData
    @GET("v1/profile/{platform}/{epic_nickname}")
    fun getUserStatsRx(
        @Path("platform") platform: String,
        @Path("epic_nickname") epic_nickname: String
    ): Observable<AccountStats>
}