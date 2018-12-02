package fortnite.eugene.com.fortnitetracker.model.stats

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("p2") val lifetimeSolo: StatsInfo?,
    @SerializedName("p10") val lifetimeDuo: StatsInfo?,
    @SerializedName("p9") val lifetimeSquads: StatsInfo?,
    @SerializedName("curr_p2") val seasonSolo: StatsInfo?,
    @SerializedName("curr_p10") val seasonDuo: StatsInfo?,
    @SerializedName("curr_p9") val seasonSquads: StatsInfo?
) : Parcelable {


    fun initData() {
        if (seasonSolo != null) {
            seasonSolo.kd!!.progressComparison = checkData(seasonSolo.kd.valueDec!!, lifetimeSolo!!.kd!!.valueDec!!)
            seasonSolo.winRatio!!.progressComparison =
                    checkData(seasonSolo.winRatio.valueDec!!, lifetimeSolo!!.winRatio!!.valueDec!!)
            seasonSolo.kpg!!.progressComparison = checkData(seasonSolo.kpg.valueDec!!, lifetimeSolo!!.kpg!!.valueDec!!)
            seasonSolo.scorePerMatch!!.progressComparison =
                    checkData(seasonSolo.scorePerMatch.valueDec!!, lifetimeSolo!!.scorePerMatch!!.valueDec!!)
        }

        if (seasonDuo != null) {
            seasonDuo.kd!!.progressComparison = checkData(seasonDuo.kd.valueDec!!, lifetimeDuo!!.kd!!.valueDec!!)
            seasonDuo.winRatio!!.progressComparison =
                    checkData(seasonDuo.winRatio.valueDec!!, lifetimeDuo!!.winRatio!!.valueDec!!)
            seasonDuo.kpg!!.progressComparison = checkData(seasonDuo.kpg.valueDec!!, lifetimeDuo!!.kpg!!.valueDec!!)
            seasonDuo.scorePerMatch!!.progressComparison =
                    checkData(seasonDuo.scorePerMatch.valueDec!!, lifetimeDuo!!.scorePerMatch!!.valueDec!!)
        }

        if (seasonSquads != null) {
            seasonSquads.kd!!.progressComparison =
                    checkData(seasonSquads.kd.valueDec!!, lifetimeSquads!!.kd!!.valueDec!!)
            seasonSquads.winRatio!!.progressComparison =
                    checkData(seasonSquads.winRatio.valueDec!!, lifetimeSquads!!.winRatio!!.valueDec!!)

            seasonSquads.kpg!!.progressComparison =
                    checkData(seasonSquads.kpg.valueDec!!, lifetimeSquads!!.kpg!!.valueDec!!)
            seasonSquads.scorePerMatch!!.progressComparison =
                    checkData(seasonSquads.scorePerMatch.valueDec!!, lifetimeSquads!!.scorePerMatch!!.valueDec!!)
        }
    }

    private fun checkData(season: Double? = null, lifetime: Double? = null): Double? {
        if (season == null || lifetime == null) {
            return null
        }
        return season.minus(lifetime)
    }

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(StatsInfo::class.java.classLoader),
        parcel.readParcelable(StatsInfo::class.java.classLoader),
        parcel.readParcelable(StatsInfo::class.java.classLoader),
        parcel.readParcelable(StatsInfo::class.java.classLoader),
        parcel.readParcelable(StatsInfo::class.java.classLoader),
        parcel.readParcelable(StatsInfo::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(lifetimeSolo, flags)
        parcel.writeParcelable(lifetimeDuo, flags)
        parcel.writeParcelable(lifetimeSquads, flags)
        parcel.writeParcelable(seasonSolo, flags)
        parcel.writeParcelable(seasonDuo, flags)
        parcel.writeParcelable(seasonSquads, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stats> {
        override fun createFromParcel(parcel: Parcel): Stats {
            return Stats(parcel)
        }

        override fun newArray(size: Int): Array<Stats?> {
            return arrayOfNulls(size)
        }
    }
}