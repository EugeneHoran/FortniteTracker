package fortnite.eugene.com.fortnitetracker.ui.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fortnite.eugene.com.fortnitetracker.App
import fortnite.eugene.com.fortnitetracker.data.dao.UserAccountDao
import fortnite.eugene.com.fortnitetracker.data.service.StatsService
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.model.stats.StatsInfo
import fortnite.eugene.com.fortnitetracker.utils.Constants
import fortnite.eugene.com.fortnitetracker.utils.SingleLiveEvent
import javax.inject.Inject

class StatsViewModel : ViewModel() {
    @Inject
    lateinit var statsService: StatsService
    @Inject
    lateinit var userAccountDao: UserAccountDao

    var error: SingleLiveEvent<String> = SingleLiveEvent()
    var userStats: MediatorLiveData<AccountStats> = MediatorLiveData()
    var seasonToggle: Int = Constants.SEASON_LIFETIME
    var soloStats: MutableLiveData<StatsInfo> = MutableLiveData()
    var duoStats: MutableLiveData<StatsInfo> = MutableLiveData()
    var squadStats: MutableLiveData<StatsInfo> = MutableLiveData()

    init {
        App.graph.inject(this)
    }

    fun getUserStats(platform: String, epicUserHandle: String): LiveData<AccountStats> {
        userStats.addSource(statsService.getUserStats(platform, epicUserHandle)) {
            if (it != null && it.resource != null) {
                if (it.resource!!.error == null) {
                    userStats.value = it.resource
                    updateStatFragments(seasonToggle)
                } else {
                    userStats.value = null
                    error.value = it.resource!!.error
                }
            }
        }
        return userStats
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

    private fun resetData() {
        userStats.value = null
        seasonToggle = Constants.SEASON_LIFETIME
        soloStats.value = null
        duoStats.value = null
        squadStats.value = null
    }
}