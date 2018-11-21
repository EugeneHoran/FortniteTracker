package fortnite.eugene.com.fortnitetracker.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.data.dao.UserAccountDao
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import fortnite.eugene.com.fortnitetracker.utils.SingleLiveEvent
import fortnite.eugene.com.fortnitetracker.utils.ioThread
import javax.inject.Inject

class LoginViewModel(private val userDao: UserAccountDao) : BaseViewModel() {

    @Inject
    lateinit var fortniteTrackerApi: FortniteTrackerApi

    var loginStatus: MutableLiveData<Boolean> = MutableLiveData()
    var userAccountList: LiveData<List<UserAccount>> = userDao.getUserList()
    var userAccount: LiveData<UserAccount> = userDao.getLatestUser()
    var userStats: MediatorLiveData<AccountStats> = MediatorLiveData()
    var error: SingleLiveEvent<String> = SingleLiveEvent()
    var showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    init {
        loginStatus.value = false
    }

    fun getUserStats(platform: String, epicUser: String) {
        showLoading.value = true
        userStats.addSource(fortniteTrackerApi.getUserStats(platform, epicUser)) {
            if (it != null) {
                if (it.error != null) {
                    error.value = it.error!!.message
                    showLoading.value = false
                    loginStatus.value = false
                } else {
                    if (it.resource!!.error == null) {
                        ioThread {
                            userDao.insert(it.resource!!.getUserAccount()!!)
                        }
                        userStats.value = it.resource
                        loginStatus.value = true
                    } else {
                        error.value = it.resource!!.error
                        showLoading.value = false
                        loginStatus.value = false
                    }
                }
            }
        }
    }

    fun clearSearchHistory() {
        ioThread {
            userDao.deleteAccounts(userAccountList.value!!)
        }
    }
}