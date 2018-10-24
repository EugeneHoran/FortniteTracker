package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName

data class StatsInfo(
    @SerializedName("trnRating") val trnRating: TrnRating?,
    @SerializedName("score") val score: Score?,
    @SerializedName("top1") val top1: Top?,
    @SerializedName("top3") val top3: Top?,
    @SerializedName("top5") val top5: Top?,
    @SerializedName("top6") val top6: Top?,
    @SerializedName("top10") val top10: Top?,
    @SerializedName("top12") val top12: Top?,
    @SerializedName("top25") val top25: Top?,
    @SerializedName("kd") val kd: Kd?,
    @SerializedName("winRatio") val winRatio: WinRatio?,
    @SerializedName("matches") val matches: Matches?,
    @SerializedName("kills") val kills: Kills?,
    @SerializedName("kpg") val kpg: Kpg?,
    @SerializedName("scorePerMatch") val scorePerMatch: ScorePerMatch?
)