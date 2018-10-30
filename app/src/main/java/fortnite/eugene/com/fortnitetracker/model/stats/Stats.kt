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