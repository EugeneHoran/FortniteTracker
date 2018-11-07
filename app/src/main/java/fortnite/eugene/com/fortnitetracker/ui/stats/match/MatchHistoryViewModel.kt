package fortnite.eugene.com.fortnitetracker.ui.stats.match

import androidx.lifecycle.MediatorLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistory
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistoryHeader
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistoryItem
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import javax.inject.Inject


class MatchHistoryViewModel : BaseViewModel() {
    @Inject
    lateinit var fortniteTrackerApi: FortniteTrackerApi
    var matchHistory: MediatorLiveData<List<MatchHistoryItem>> = MediatorLiveData()

    fun getMatchHistory(accountId: String) {
        matchHistory.addSource(fortniteTrackerApi.getUserMatches(accountId)) {
            if (it != null) {
                if (it.error != null) {
                } else {
                    matchHistory.value = getMatchHistoryDisplayData(it.resource!!.sortedByDescending { match ->
                        match.getDate()
                    })
                }
            }
        }
    }

    private fun getMatchHistoryDisplayData(matchHistoryList: List<MatchHistory>): List<MatchHistoryItem> {
        val matchGroupList = mutableListOf<MatchHistoryItem>()
        if (matchHistoryList.isEmpty()) {
            return matchGroupList
        }
        val groupHistoryByDate = matchHistoryList.groupBy { it.getDateClean() }
        groupHistoryByDate.forEach {
            val matchHistoryHeader = MatchHistoryHeader(it.key.toString(), it.key!!)
            it.value.forEach { match ->
                matchHistoryHeader.addMatches(match.matches!!)
                matchHistoryHeader.addKills(match.kills!!)
                matchHistoryHeader.addWins(match.top1!!)
            }
            matchGroupList.add(matchHistoryHeader)
            matchGroupList.addAll(it.value)
        }
        return matchGroupList
    }
}