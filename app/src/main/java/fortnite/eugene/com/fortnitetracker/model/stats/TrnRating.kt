package fortnite.eugene.com.fortnitetracker.model.stats

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*

data class TrnRating(
    @SerializedName("label") val label: String?,
    @SerializedName("field") val field: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("valueInt") val valueInt: Int?,
    @SerializedName("value") val value: String?,
    @SerializedName("rank") val rank: Int?,
    @SerializedName("percentile") val percentile: Double?,
    @SerializedName("displayValue") val displayValue: String?
) : DisplayStatsItem(), Parcelable {
    override fun getProgress(): Double? = null
    override fun getTitle(): String? {
        return label
    }

    override fun getDisplayText(): String? {
        return displayValue
    }

    override fun getItemTopPercent(): String? {
        return if (percentile == null) null else percentile.toString()
    }

    override fun getItemPercentile(): Double? {
        return if (percentile == null) null else (100 - percentile)
    }

    override fun getItemRank(): String? {
        return if (rank == null) null else "#" + NumberFormat.getNumberInstance(Locale.US).format(rank)

    }

    /**
     * Parcel
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(label)
        parcel.writeString(field)
        parcel.writeString(category)
        parcel.writeValue(valueInt)
        parcel.writeString(value)
        parcel.writeValue(rank)
        parcel.writeValue(percentile)
        parcel.writeString(displayValue)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<TrnRating> {
        override fun createFromParcel(parcel: Parcel): TrnRating {
            return TrnRating(parcel)
        }

        override fun newArray(size: Int): Array<TrnRating?> {
            return arrayOfNulls(size)
        }
    }
}