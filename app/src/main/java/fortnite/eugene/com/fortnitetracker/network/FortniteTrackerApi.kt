package fortnite.eugene.com.fortnitetracker.network

import androidx.lifecycle.LiveData
import com.github.leonardoxh.livedatacalladapter.Resource
import fortnite.eugene.com.fortnitetracker.model.challenges.Challenges
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistory
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.model.store.StoreItem
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FortniteTrackerApi {

    @GET("v1/profile/{platform}/{epic_nickname}")
    fun getUserStats(
        @Path("platform") platform: String,
        @Path("epic_nickname") epic_nickname: String
    ): LiveData<Resource<AccountStats>>

    @GET("v1/profile/account/{accountId}/matches")
    fun getUserMatches(
        @Path("accountId") accountId: String
    ): LiveData<Resource<List<MatchHistory>>>

    @GET("v1/challenges")
    fun getChallenges(): LiveData<Resource<Challenges>>

    @GET("v1/challenges")
    fun getChallenge(): Observable<Response<Challenges>>

    @GET("v1/store")
    fun getStore(): Observable<Response<List<StoreItem>>>


    @GET("v1/profile/{platform}/{epic_nickname}")
    fun getStats(
        @Path("platform") platform: String,
        @Path("epic_nickname") epic_nickname: String
    ): Observable<Response<AccountStats>>
}