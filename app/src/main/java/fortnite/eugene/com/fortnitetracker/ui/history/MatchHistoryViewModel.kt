package fortnite.eugene.com.fortnitetracker.ui.history

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistory
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistoryHeader
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistoryItem
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject


class MatchHistoryViewModel(private val accountId: String) : BaseViewModel<Any>() {
    @Inject
    lateinit var fortniteTrackerApi: FortniteTrackerApi
    var matchHistory: MediatorLiveData<List<MatchHistoryItem>> = MediatorLiveData()
    var showLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        refreshData()
    }

    fun refreshData() {
        showLoading.value = true
        initData()
    }

    private fun initData() {
        getCompositeDisposable().add(
            fortniteTrackerApi.getMatchHistory(accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { matchHistoryListResponse: Response<List<MatchHistory>> ->
                    val matchGroupList = mutableListOf<MatchHistoryItem>()
                    if (matchHistoryListResponse.isSuccessful) {
                        matchHistoryListResponse.body()!!.sortedByDescending { matchHistory ->
                            matchHistory.dateCollected
                        }
                        val groupHistoryByDate =
                            matchHistoryListResponse.body()!!.groupBy { it.getFilteredDateFormat() }
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
                        return@map matchGroupList
                    } else {
                        return@map matchGroupList
                    }
                }.subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(matchHistoryList: List<MatchHistoryItem>) {
        matchHistory.value = matchHistoryList
        showLoading.value = false
    }

    private fun handleError(throwable: Throwable) {
        showLoading.value = false
        error(throwable.message!!)
    }

}