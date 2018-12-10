package fortnite.eugene.com.fortnitetracker.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.data.dao.UserAccountDao
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import fortnite.eugene.com.fortnitetracker.utils.Constants
import fortnite.eugene.com.fortnitetracker.utils.SingleLiveEvent
import fortnite.eugene.com.fortnitetracker.utils.ioThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel(private val userDao: UserAccountDao) : BaseViewModel() {

    @Inject
    lateinit var fortniteTrackerApi: FortniteTrackerApi

    var loginStatus: MutableLiveData<Boolean> = MutableLiveData()
    var userAccountList: LiveData<List<UserAccount>> = userDao.getUserList()
    var userAccount: LiveData<UserAccount> = userDao.getLatestUser()
    var userStats: MutableLiveData<AccountStats> = MutableLiveData()
    var error: SingleLiveEvent<String> = SingleLiveEvent()
    var showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    private var deletedAccount: UserAccount? = null

    init {
        loginStatus.value = false
    }

    fun getStats(platform: String, epicUser: String) {
        showLoading.value = true
        getCompositeDisposable().add(
            fortniteTrackerApi.getStats(platform, formatEpicUserByPlatform(platform, epicUser))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    this.handleResponse(response, epicUser)
                }, this::handleError)
        )
    }

    private fun handleResponse(response: AccountStats?, epicUser: String) {
        if (response!!.error == null) {
            ioThread {
                userDao.insert(response.getUserAccount(epicUser)!!)
            }
            userStats.value = response
            loginStatus.value = true
        } else {
            error.value = response.error
            showLoading.value = false
            loginStatus.value = false
        }
    }

    private fun handleError(throwable: Throwable) {
        errorResponse(throwable.message!!)
        showLoading.value = false
    }

    private fun errorResponse(message: String) {
        error.value = message
        showLoading.value = false
    }

    private fun formatEpicUserByPlatform(platform: String, epicUser: String): String {
        return when (platform) {
            Constants.PLATFORM_XBOX_STRING, "xbox" -> Constants.PLATFORM_XBOX_STRING + "(" + epicUser + ")"
//            Constants.PLATFORM_PS4_STRING -> Constants.PLATFORM_PS4_STRING + "(" + epicUser + ")"
            else -> epicUser
        }
    }


    fun clearSearchHistory() {
        ioThread {
            userDao.deleteAccounts(userAccountList.value!!)
        }
    }

    fun deleteAccount(userAccount: UserAccount) {
        deletedAccount = UserAccount(userAccount)
        ioThread {
            userDao.deleteAccount(userAccount)
        }
    }

    fun undoDeletedAccount() {
        if (deletedAccount != null) {
            ioThread {
                userDao.insert(deletedAccount!!)
                deletedAccount = null
            }
        }
    }
}