package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName

data class Kpg(
    @SerializedName("label") val label: String?,
    @SerializedName("field") val field: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("valueDec") val valueDec: Double?,
    @SerializedName("value") val value: String?,
    @SerializedName("rank") val rank: Int?,
    @SerializedName("percentile") val percentile: Double?,
    @SerializedName("displayValue") val displayValue: String?
)