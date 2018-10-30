package fortnite.eugene.com.fortnitetracker.model.stats

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(accountId)
        parcel.writeString(playlist)
        parcel.writeValue(kills)
        parcel.writeValue(minutesPlayed)
        parcel.writeValue(top1)
        parcel.writeValue(top5)
        parcel.writeValue(top6)
        parcel.writeValue(top10)
        parcel.writeValue(top12)
        parcel.writeValue(top25)
        parcel.writeValue(matches)
        parcel.writeValue(top3)
        parcel.writeString(dateCollected)
        parcel.writeValue(score)
        parcel.writeValue(platform)
        parcel.writeValue(trnRating)
        parcel.writeValue(trnRatingChange)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecentMatche> {
        override fun createFromParcel(parcel: Parcel): RecentMatche {
            return RecentMatche(parcel)
        }

        override fun newArray(size: Int): Array<RecentMatche?> {
            return arrayOfNulls(size)
        }
    }
}