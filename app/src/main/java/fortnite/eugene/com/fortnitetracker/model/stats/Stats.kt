package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("p2") val lifetimeSolo: StatsInfo?,
    @SerializedName("p10") val lifetimeDuo: StatsInfo?,
    @SerializedName("p9") val lifetimeSquads: StatsInfo?,
    @SerializedName("curr_p2") val seasonSolo: StatsInfo?,
    @SerializedName("curr_p10") val seasonDuo: StatsInfo?,
    @SerializedName("curr_p9") val seasonSquads: StatsInfo?
)