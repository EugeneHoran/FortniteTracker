package fortnite.eugene.com.fortnitetracker.data.service

import androidx.lifecycle.LiveData
import com.github.leonardoxh.livedatacalladapter.Resource
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import retrofit2.http.GET
import retrofit2.http.Path

interface StatsService {
    @GET("v1/profile/{platform}/{epic_nickname}")
    fun getUserStats(
        @Path("platform") platform: String,
        @Path("epic_nickname") epic_nickname: String
    ): LiveData<Resource<AccountStats>>
}