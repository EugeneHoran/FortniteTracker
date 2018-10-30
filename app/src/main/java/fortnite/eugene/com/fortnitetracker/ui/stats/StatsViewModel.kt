package fortnite.eugene.com.fortnitetracker.ui.stats

import android.util.Log
import androidx.lifecycle.LiveData
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

    var platform: String? = null
    var epicUser: String? = null

    var error: SingleLiveEvent<String> = SingleLiveEvent()
    var userStats: MutableLiveData<AccountStats> = MutableLiveData()
    var seasonToggle: Int = Constants.SEASON_LIFETIME
    var soloStats: MutableLiveData<StatsInfo> = MutableLiveData()
    var duoStats: MutableLiveData<StatsInfo> = MutableLiveData()
    var squadStats: MutableLiveData<StatsInfo> = MutableLiveData()
    var userSignedIn: Boolean = false

    init {
        App.graph.inject(this)
    }

    fun setUserStats(accountStats: AccountStats) {
        userStats.value = accountStats
        updateStatFragments(Constants.SEASON_LIFETIME)
    }

    fun getUserStats(): LiveData<AccountStats> {
        return userStats
    }

    fun updateStatFragments(whichSeason: Int) {
        seasonToggle = whichSeason
        if (userStats.value == null || userStats.value!!.error != null) {
            return
        }
        when (seasonToggle) {
            Constants.SEASON_LIFETIME -> {
                Log.e("Testing", "updateStatFragments()")
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