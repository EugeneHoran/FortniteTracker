package fortnite.eugene.com.fortnitetracker.model.stats

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.ColorInt
import com.google.gson.annotations.SerializedName
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import java.text.DecimalFormat
import java.util.*

data class AccountStats(
    @SerializedName("error") val error: String?,
    @SerializedName("accountId") val accountId: String?,
    @SerializedName("epicUserHandle") val epicUserHandle: String?,
    @SerializedName("platformId") val platformId: Int?,
    @SerializedName("platformName") val platformName: String?,
    @SerializedName("platformNameLong") val platformNameLong: String?,
    @SerializedName("stats") val stats: Stats?,
    @SerializedName("lifeTimeStats") val lifeTimeStats: List<LifeTimeStat?>?
) : Parcelable {

    fun getDisplayNameFormatted(): String {
        return epicUserHandle!!.replace("xbl(", "").replace(")", "")
    }

    fun getSummaryData(): List<Any> {
        val summaryList = mutableListOf<Any>()
        summaryList.add(getMatchesPieChardData() as Any)
        summaryList.addAll(lifeTimeStats!!.reversed() as List<Any>)
        return summaryList
    }

    @ColorInt
    fun getSoloColor(): Int = Color.parseColor("#8399EC")

    @ColorInt
    fun getDuoColor(): Int = Color.parseColor("#7DDDDC")

    @ColorInt
    fun getSquadColor(): Int = Color.parseColor("#2DCB76")

    private fun getMatchesPieChardData(): ChartDataItem? {
        if (stats == null) {
            return null
        }
        val df = DecimalFormat("0.00")
        val soloMatchesPlayed = stats.lifetimeSolo!!.matches!!.valueInt!!.toDouble()
        val duoMatchesPlayed = stats.lifetimeDuo!!.matches!!.valueInt!!.toDouble()
        val squadMatchesPlayed = stats.lifetimeSquads!!.matches!!.valueInt!!.toDouble()
        val totalMatchesPlayed = soloMatchesPlayed + duoMatchesPlayed + squadMatchesPlayed

        val soloPercentage = soloMatchesPlayed.div(totalMatchesPlayed) * 100
        val duoPercentage = duoMatchesPlayed.div(totalMatchesPlayed) * 100
        val squadPercentage = squadMatchesPlayed.div(totalMatchesPlayed) * 100

        val soloLabel = "Solo : " + df.format(soloPercentage) + "%"
        val duoLabel = "Duo : " + df.format(duoPercentage) + "%"
        val squadLabel = "Squad : " + df.format(squadPercentage) + "%"

        val pieData = mutableListOf<SliceValue>()
        pieData.add(SliceValue(soloPercentage.toFloat(), getSoloColor()).setLabel(soloLabel))
        pieData.add(SliceValue(duoPercentage.toFloat(), getDuoColor()).setLabel(duoLabel))
        pieData.add(SliceValue(squadPercentage.toFloat(), getSquadColor()).setLabel(squadLabel))
        val pieChartData = PieChartData(pieData)
        pieChartData.setHasLabels(true)
        pieChartData.valueLabelTextSize = 10
        pieChartData.setHasCenterCircle(true)
        pieChartData.centerText1 = "Modes Played"
        pieChartData.slicesSpacing = 10
        pieChartData.centerText1FontSize = 10
        pieChartData.centerText1Color = Color.parseColor("#FFFFFF")


        return ChartDataItem(
            pieChartData,
            stats.lifetimeSolo.matches!!.valueInt!!,
            stats.lifetimeDuo.matches!!.valueInt!!,
            stats.lifetimeSquads.matches!!.valueInt!!
        )
    }

    fun getUserAccount(): UserAccount? {
        return UserAccount(
            accountId!!,
            epicUserHandle!!,
            platformId!!,
            platformName!!,
            platformNameLong!!,
            Date().time,
            ""// TODO
        )
    }

    fun getUserAccount(displayName: String): UserAccount? {
        return UserAccount(
            accountId!!,
            epicUserHandle!!,
            platformId!!,
            platformName!!,
            platformNameLong!!,
            Date().time,
            displayName
        )
    }

    fun getLogoInt(): Int {
        return mapOf(1 to R.drawable.ic_xbox, 2 to R.drawable.ic_playstation, 3 to R.drawable.ic_pc)[platformId]!!
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Stats::class.java.classLoader),
        parcel.createTypedArrayList(LifeTimeStat)
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