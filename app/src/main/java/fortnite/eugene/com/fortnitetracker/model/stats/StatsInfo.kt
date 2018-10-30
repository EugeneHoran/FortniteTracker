package fortnite.eugene.com.fortnitetracker.model.stats

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    fun getDisplayStats(): List<DisplayStatsItem> {
        val statsItemList = mutableListOf<DisplayStatsItem>()
        statsItemList.add(matches!!)
        statsItemList.add(kd!!)
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

    /**
     * Parcel
     */
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(TrnRating::class.java.classLoader),
        parcel.readParcelable(Score::class.java.classLoader),
        parcel.readParcelable(Top::class.java.classLoader),
        parcel.readParcelable(Top::class.java.classLoader),
        parcel.readParcelable(Top::class.java.classLoader),
        parcel.readParcelable(Top::class.java.classLoader),
        parcel.readParcelable(Top::class.java.classLoader),
        parcel.readParcelable(Top::class.java.classLoader),
        parcel.readParcelable(Top::class.java.classLoader),
        parcel.readParcelable(Kd::class.java.classLoader),
        parcel.readParcelable(WinRatio::class.java.classLoader),
        parcel.readParcelable(Matches::class.java.classLoader),
        parcel.readParcelable(Kills::class.java.classLoader),
        parcel.readParcelable(Kpg::class.java.classLoader),
        parcel.readParcelable(ScorePerMatch::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(trnRating, flags)
        parcel.writeParcelable(score, flags)
        parcel.writeParcelable(top1, flags)
        parcel.writeParcelable(top3, flags)
        parcel.writeParcelable(top5, flags)
        parcel.writeParcelable(top6, flags)
        parcel.writeParcelable(top10, flags)
        parcel.writeParcelable(top12, flags)
        parcel.writeParcelable(top25, flags)
        parcel.writeParcelable(kd, flags)
        parcel.writeParcelable(winRatio, flags)
        parcel.writeParcelable(matches, flags)
        parcel.writeParcelable(kills, flags)
        parcel.writeParcelable(kpg, flags)
        parcel.writeParcelable(scorePerMatch, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StatsInfo> {
        override fun createFromParcel(parcel: Parcel): StatsInfo {
            return StatsInfo(parcel)
        }

        override fun newArray(size: Int): Array<StatsInfo?> {
            return arrayOfNulls(size)
        }
    }
}