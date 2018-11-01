package fortnite.eugene.com.fortnitetracker.ui.stats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.model.stats.StatsInfo
import fortnite.eugene.com.fortnitetracker.utils.Constants

class StatsViewModel : ViewModel() {
    var userStats: MutableLiveData<AccountStats> = MutableLiveData()
    var seasonToggle: Int = Constants.SEASON_LIFETIME
    var soloStats: MutableLiveData<StatsInfo> = MutableLiveData()
    var duoStats: MutableLiveData<StatsInfo> = MutableLiveData()
    var squadStats: MutableLiveData<StatsInfo> = MutableLiveData()

    fun setUserStats(accountStats: AccountStats) {
        userStats.value = accountStats
        updateStatFragments(seasonToggle)
    }

    fun updateStatFragments(whichSeason: Int) {
        seasonToggle = whichSeason
        if (userStats.value == null || userStats.value!!.error != null) {
            return
        }
        when (seasonToggle) {
            Constants.SEASON_LIFETIME -> {
                soloStats.value = userStats.value!!.stats!!.lifetimeSolo
                duoStats.value = userStats.value!!.stats!!.lifetimeDuo
                squadStats.value = userStats.value!!.stats!!.lifetimeSquads
            }
            Constants.SEASON_CURRENT -> {
                soloStats.value = userStats.value!!.stats!!.seasonSolo
                duoStats.value = userStats.value!!.stats!!.seasonDuo
                squadStats.value = userStats.value!!.stats!!.seasonSquads
            }
        }
    }
}