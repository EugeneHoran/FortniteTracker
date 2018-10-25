package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*

data class ScorePerMatch(
    @SerializedName("label") val label: String?,
    @SerializedName("field") val field: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("valueDec") val valueDec: Double?,
    @SerializedName("value") val value: String?,
    @SerializedName("rank") val rank: Int?,
    @SerializedName("percentile") val percentile: Double?,
    @SerializedName("displayValue") val displayValue: String?
) : DisplayStatsItem() {
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
}