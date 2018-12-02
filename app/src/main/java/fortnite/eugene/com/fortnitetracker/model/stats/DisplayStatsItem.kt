package fortnite.eugene.com.fortnitetracker.model.stats

import fortnite.eugene.com.fortnitetracker.R
import java.text.DecimalFormat

abstract class DisplayStatsItem {
    abstract fun getTitle(): String?

    abstract fun getDisplayText(): String?

    abstract fun getItemTopPercent(): String?

    abstract fun getItemPercentile(): Double?

    abstract fun getItemRank(): String?

    abstract fun getProgress(): Double?

    override fun toString(): String {
        return getTitle().toString() + getDisplayText().toString() + getItemTopPercent().toString() + getItemPercentile().toString() + getItemRank().toString() + getProgress().toString()
    }

    fun getDisplayProgress(): String? {
        val df = DecimalFormat("0.00")
        return if (getProgress() != null) {
            df.format(getProgress())
        } else {
            null
        }
    }

    fun getArror(): Int? {
        return when {
            getProgress()!! > 0 -> R.drawable.ic_arrow_up
            getProgress()!! < 0 -> R.drawable.ic_arrow_down
            else -> null
        }
    }

    fun getTextColor(): Int? {
        return when {
            getProgress()!! > 0 -> R.color.colorPositive
            getProgress()!! < 0 -> R.color.colorNegative
            else -> null
        }
    }

}