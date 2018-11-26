package fortnite.eugene.com.fortnitetracker.model.stats

import lecho.lib.hellocharts.model.PieChartData

data class ChartDataItem(
    var pieChartData: PieChartData,
    var soloMatched: Int,
    var duoMatches: Int,
    var squadMatches: Int
)