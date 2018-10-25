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
) {
    fun getDisplayStats(): List<DisplayStatsItem> {
        val statsItemList = mutableListOf<DisplayStatsItem>()
        statsItemList.add(kd!!)
        statsItemList.add(matches!!)
        statsItemList.add(kills!!)
        statsItemList.add(kpg!!)
        statsItemList.add(top1!!)
        if (winRatio != null) {
            statsItemList.add(winRatio)
        } else {
            statsItemList.add(WinRatio())
        }
        if (!top3!!.getDisplayText().equals("0", true)) {
            statsItemList.add(top3)
        }
        if (!top5!!.getDisplayText().equals("0", true)) {
            statsItemList.add(top5)
        }
        if (!top6!!.getDisplayText().equals("0", true)) {
            statsItemList.add(top6)
        }
        if (!top10!!.getDisplayText().equals("0", true)) {
            statsItemList.add(top10)
        }
        if (!top12!!.getDisplayText().equals("0", true)) {
            statsItemList.add(top12)
        }
        if (!top25!!.getDisplayText().equals("0", true)) {
            statsItemList.add(top25)
        }
        return statsItemList
    }
}