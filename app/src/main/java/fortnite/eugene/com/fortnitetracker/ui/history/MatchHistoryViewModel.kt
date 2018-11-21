package fortnite.eugene.com.fortnitetracker.ui.history

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistory
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistoryHeader
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistoryItem
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import javax.inject.Inject


class MatchHistoryViewModel(private val accountId: String) : BaseViewModel() {
    @Inject
    lateinit var fortniteTrackerApi: FortniteTrackerApi
    var matchHistory: MediatorLiveData<List<MatchHistoryItem>> = MediatorLiveData()
    var showLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        refreshData()
    }

    fun refreshData() {
        showLoading.value = true
        matchHistory.addSource(fortniteTrackerApi.getUserMatches(accountId)) {
            if (it != null) {
                if (it.error != null) {
                } else {
                    matchHistory.value = getMatchHistoryDisplayData(it.resource!!.sortedByDescending { matchHistory ->
                        matchHistory.dateCollected
                    })
                }
            }
            showLoading.value = false
        }
    }

    private fun getMatchHistoryDisplayData(matchHistoryList: List<MatchHistory>): List<MatchHistoryItem> {
        val matchGroupList = mutableListOf<MatchHistoryItem>()
        if (matchHistoryList.isEmpty()) {
            return matchGroupList
        }
        val groupHistoryByDate = matchHistoryList.groupBy { it.getFilteredDateFormat() }
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