package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName

data class RecentMatche(
    @SerializedName("id") val id: Int?,
    @SerializedName("accountId") val accountId: String?,
    @SerializedName("playlist") val playlist: String?,
    @SerializedName("kills") val kills: Int?,
    @SerializedName("minutesPlayed") val minutesPlayed: Int?,
    @SerializedName("top1") val top1: Int?,
    @SerializedName("top5") val top5: Int?,
    @SerializedName("top6") val top6: Int?,
    @SerializedName("top10") val top10: Int?,
    @SerializedName("top12") val top12: Int?,
    @SerializedName("top25") val top25: Int?,
    @SerializedName("matches") val matches: Int?,
    @SerializedName("top3") val top3: Int?,
    @SerializedName("dateCollected") val dateCollected: String?,
    @SerializedName("score") val score: Int?,
    @SerializedName("platform") val platform: Int?,
    @SerializedName("trnRating") val trnRating: Double?,
    @SerializedName("trnRatingChange") val trnRatingChange: Double?
)