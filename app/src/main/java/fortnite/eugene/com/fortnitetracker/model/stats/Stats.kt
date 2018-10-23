package fortnite.eugene.com.fortnitetracker.model.stats

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("p2") val p2: P2?,
    @SerializedName("p10") val p10: P10?,
    @SerializedName("p9") val p9: P9?,
    @SerializedName("curr_p2") val currP2: CurrP2?,
    @SerializedName("curr_p10") val currP10: CurrP10?,
    @SerializedName("curr_p9") val currP9: CurrP9?
)