package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName

data class P2(
    @SerializedName("trnRating") val trnRating: TrnRating?,
    @SerializedName("score") val score: Score?,
    @SerializedName("top1") val top1: Top1?,
    @SerializedName("top3") val top3: Top3?,
    @SerializedName("top5") val top5: Top5?,
    @SerializedName("top6") val top6: Top6?,
    @SerializedName("top10") val top10: Top10?,
    @SerializedName("top12") val top12: Top12?,
    @SerializedName("top25") val top25: Top25?,
    @SerializedName("kd") val kd: Kd?,
    @SerializedName("winRatio") val winRatio: WinRatio?,
    @SerializedName("matches") val matches: Matches?,
    @SerializedName("kills") val kills: Kills?,
    @SerializedName("kpg") val kpg: Kpg?,
    @SerializedName("scorePerMatch") val scorePerMatch: ScorePerMatch?
)