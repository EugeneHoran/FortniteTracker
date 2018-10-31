package fortnite.eugene.com.fortnitetracker.model.stats

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import java.util.*

data class AccountStats(
    @SerializedName("error") val error: String?,
    @SerializedName("accountId") val accountId: String?,
    @SerializedName("epicUserHandle") val epicUserHandle: String?,
    @SerializedName("platformId") val platformId: Int?,
    @SerializedName("platformName") val platformName: String?,
    @SerializedName("platformNameLong") val platformNameLong: String?,
    @SerializedName("stats") val stats: Stats?,
    @SerializedName("lifeTimeStats") val lifeTimeStats: List<LifeTimeStat?>?,
    @SerializedName("recentMatches") val recentMatches: List<RecentMatche?>?
) : Parcelable {

    fun getUserAccount(): UserAccount? {
        return UserAccount(
            accountId!!,
            epicUserHandle!!,
            platformId!!,
            platformName!!,
            platformNameLong!!,
            Date().time
        )
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Stats::class.java.classLoader),
        parcel.createTypedArrayList(LifeTimeStat),
        parcel.createTypedArrayList(RecentMatche)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(error)
        parcel.writeString(accountId)
        parcel.writeString(epicUserHandle)
        parcel.writeValue(platformId)
        parcel.writeString(platformName)
        parcel.writeString(platformNameLong)
        parcel.writeParcelable(stats, flags)
        parcel.writeTypedList(lifeTimeStats)
        parcel.writeTypedList(recentMatches)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountStats> {
        override fun createFromParcel(parcel: Parcel): AccountStats {
            return AccountStats(parcel)
        }

        override fun newArray(size: Int): Array<AccountStats?> {
            return arrayOfNulls(size)
        }
    }
}