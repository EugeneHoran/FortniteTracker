package fortnite.eugene.com.fortnitetracker.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import fortnite.eugene.com.fortnitetracker.App
import fortnite.eugene.com.fortnitetracker.data.dao.UserAccountDao
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import fortnite.eugene.com.fortnitetracker.data.service.StatsService
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.utils.Constants
import fortnite.eugene.com.fortnitetracker.utils.SingleLiveEvent
import fortnite.eugene.com.fortnitetracker.utils.ioThread
import javax.inject.Inject

class EpicLoginViewModel : ViewModel() {
    @Inject
    lateinit var statsService: StatsService

    @Inject
    lateinit var userAccountDao: UserAccountDao

    init {
        App.graph.inject(this)
    }

    var userAccountList: LiveData<List<UserAccount>> = userAccountDao.getUserList()

    var seasonToggle: Int = Constants.SEASON_LIFETIME
    var userStats: MediatorLiveData<AccountStats> = MediatorLiveData()
    var error: SingleLiveEvent<String> = SingleLiveEvent()

    fun getUserStats(platform: String, epicUser: String) {
        seasonToggle = Constants.SEASON_LIFETIME
        userStats.addSource(statsService.getUserStats(platform, epicUser)) {
            if (it != null) {
                if (it.error != null) {
                    error.value = it.error!!.message
                } else {
                    if (it.resource!!.error == null) {
                        ioThread {
                            userAccountDao.insert(it.resource!!.getUserAccount()!!)
                        }
                        userStats.value = it.resource
                    } else {
                        error.value = it.resource!!.error
                    }
                }
            }
        }
    }
}