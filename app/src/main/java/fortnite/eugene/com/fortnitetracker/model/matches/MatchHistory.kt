package fortnite.eugene.com.fortnitetracker.model.matches

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

data class MatchHistory(
    @SerializedName("dateCollected")
    val dateCollected: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("kills")
    val kills: Int?,
    @SerializedName("matches")
    val matches: Int?,
    @SerializedName("playlist")
    val playlist: String?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("top1")
    val top1: Int?,
    @SerializedName("top10")
    val top10: Int?,
    @SerializedName("top12")
    val top12: Int?,
    @SerializedName("top25")
    val top25: Int?,
    @SerializedName("top3")
    val top3: Int?,
    @SerializedName("top5")
    val top5: Int?,
    @SerializedName("top6")
    val top6: Int?,
    @SerializedName("trnRating")
    val trnRating: Double?
) : MatchHistoryItem() {

    @SuppressLint("SimpleDateFormat")
    fun getFilteredDateFormat(): Date? = try {
        SimpleDateFormat("yyyy-MM-dd").parse(dateCollected!!.split("T")[0])
    } catch (exception: Exception) {
        null
    }

    fun getKd(): String {
        val kd = kills!!.toFloat().div(matches!! - top1!!)
        val df = DecimalFormat("0.00").apply {
            roundingMode = RoundingMode.CEILING
        }
        return df.format(kd)
    }

    fun getDisplayPlaylist(): String {
        return when (playlist) {
            "p9" -> "Squads"
            "p10" -> "Duos"
            "p2" -> "Solo"
            else -> ""
        }
    }
}