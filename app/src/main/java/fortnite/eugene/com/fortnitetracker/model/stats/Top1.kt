package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName

data class Top1(
    @SerializedName("label") val label: String?,
    @SerializedName("field") val field: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("valueInt") val valueInt: Int?,
    @SerializedName("value") val value: String?,
    @SerializedName("rank") val rank: Int?,
    @SerializedName("percentile") val percentile: Double?,
    @SerializedName("displayValue") val displayValue: String?
)