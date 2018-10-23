package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName

data class AccountStats(
    @SerializedName("accountId") val accountId: String?,
    @SerializedName("platformId") val platformId: Int?,
    @SerializedName("platformName") val platformName: String?,
    @SerializedName("platformNameLong") val platformNameLong: String?,
    @SerializedName("epicUserHandle") val epicUserHandle: String?,
    @SerializedName("stats") val stats: Stats?,
    @SerializedName("lifeTimeStats") val lifeTimeStats: List<LifeTimeStat?>?,
    @SerializedName("recentMatches") val recentMatches: List<RecentMatche?>?
)