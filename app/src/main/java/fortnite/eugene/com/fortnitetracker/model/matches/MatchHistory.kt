package fortnite.eugene.com.fortnitetracker.model.matches

import com.google.gson.annotations.SerializedName
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
    fun getDateClean(): Date? = try {
        SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateCollected!!.split("T")[0])
    } catch (exception: Exception) {
        null
    }

    fun getDateDisplay(): String? = try {
        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(dateCollected!!.split("T")[0])
    } catch (exception: Exception) {
        null
    }

    fun getDate(): Date? = try {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(dateCollected)
    } catch (exception: Exception) {
        null
    }
}