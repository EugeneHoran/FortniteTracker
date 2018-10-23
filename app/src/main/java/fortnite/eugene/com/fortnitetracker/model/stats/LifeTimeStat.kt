package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName

data class LifeTimeStat(
    @SerializedName("key") val key: String?,
    @SerializedName("value") val value: String?
)