package fortnite.eugene.com.fortnitetracker.model.matches

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

data class MatchHistoryHeader(
    val dateCollected: String,
    val date: Date,
    var matches: Int = 0,
    var wins: Int = 0,
    var kills: Int = 0
) : MatchHistoryItem() {

    fun getDateDisplay(): String? = try {
        SimpleDateFormat("EE, MMM d, yyyy", Locale.US).format(date)
    } catch (exception: Exception) {
        null
    }

    fun getKd(): String {
        val kd = kills.toFloat().div(matches - wins)
        val df = DecimalFormat("0.00").apply {
            roundingMode = RoundingMode.CEILING
        }
        return df.format(kd)
    }

    fun addMatches(matches: Int) {
        this.matches = this.matches + matches
    }

    fun addWins(wins: Int) {
        this.wins = this.wins + wins
    }

    fun addKills(kills: Int) {
        this.kills = this.kills + kills
    }
}