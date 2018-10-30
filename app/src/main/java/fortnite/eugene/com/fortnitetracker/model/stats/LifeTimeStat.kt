package fortnite.eugene.com.fortnitetracker.model.stats

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class LifeTimeStat(
    @SerializedName("key") val key: String?,
    @SerializedName("value") val value: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LifeTimeStat> {
        override fun createFromParcel(parcel: Parcel): LifeTimeStat {
            return LifeTimeStat(parcel)
        }

        override fun newArray(size: Int): Array<LifeTimeStat?> {
            return arrayOfNulls(size)
        }
    }
}