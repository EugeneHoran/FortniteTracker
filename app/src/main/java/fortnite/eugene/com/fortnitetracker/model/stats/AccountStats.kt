package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import java.util.*

data class AccountStats(
    @SerializedName("error") val error: String?,
    @SerializedName("accountId") val accountId: String?,
    @SerializedName("epicUserHandle") val epicUserHandle: String?,
    @SerializedName("platformId") val platformId: Int?,
    @SerializedName("platformName") val platformName: String?,
    @SerializedName("platformNameLong") val platformNameLong: String?,
    @SerializedName("stats") val stats: Stats?,
    @SerializedName("lifeTimeStats") val lifeTimeStats: List<LifeTimeStat?>?,
    @SerializedName("recentMatches") val recentMatches: List<RecentMatche?>?
) {
    fun getUserAccount(): UserAccount? {
        return if (error != null)
            UserAccount(
                accountId!!,
                epicUserHandle!!,
                platformId!!,
                platformName!!,
                platformNameLong!!,
                Date().time
            )
        else null
    }
}