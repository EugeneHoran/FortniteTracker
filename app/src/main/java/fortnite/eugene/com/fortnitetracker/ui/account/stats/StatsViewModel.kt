package fortnite.eugene.com.fortnitetracker.ui.account.stats

import androidx.lifecycle.MutableLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.model.stats.StatsInfo
import fortnite.eugene.com.fortnitetracker.utils.Constants


class StatsViewModel(private val accountStats: AccountStats) : BaseViewModel() {
    var seasonToggle: Int = Constants.SEASON_COMBINED
    var soloStats: MutableLiveData<StatsInfo> = MutableLiveData()
    var duoStats: MutableLiveData<StatsInfo> = MutableLiveData()
    var squadStats: MutableLiveData<StatsInfo> = MutableLiveData()

    init {
        updateStatFragments(seasonToggle)
    }

    fun updateStatFragments(whichSeason: Int) {
        seasonToggle = whichSeason
        if (whichSeason == Constants.SEASON_COMBINED) {
            return
        }
        if (accountStats.stats != null) {
            when (seasonToggle) {
                Constants.SEASON_LIFETIME -> {
                    soloStats.value = accountStats.stats.lifetimeSolo
                    duoStats.value = accountStats.stats.lifetimeDuo
                    squadStats.value = accountStats.stats.lifetimeSquads
                }
                Constants.SEASON_CURRENT -> {
                    soloStats.value = accountStats.stats.seasonSolo
                    duoStats.value = accountStats.stats.seasonDuo
                    squadStats.value = accountStats.stats.seasonSquads
                }
            }
        }
    }
}