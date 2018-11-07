package fortnite.eugene.com.fortnitetracker.model.stats

abstract class DisplayStatsItem {
    abstract fun getTitle(): String?

    abstract fun getDisplayText(): String?

    abstract fun getItemTopPercent(): String?

    abstract fun getItemPercentile(): Double?

    abstract fun getItemRank(): String?

    override fun toString(): String {
        return getTitle().toString() + getDisplayText().toString() + getItemTopPercent().toString() + getItemPercentile().toString() + getItemRank().toString()
    }
}