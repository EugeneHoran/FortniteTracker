package fortnite.eugene.com.fortnitetracker.ui.store

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.model.store.StoreDisplayItem
import fortnite.eugene.com.fortnitetracker.model.store.StoreHeaderItem
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import fortnite.eugene.com.fortnitetracker.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class StoreViewModel : BaseViewModel() {

    @Inject
    lateinit var fortniteTrackerApi: FortniteTrackerApi

    var storeItems = MediatorLiveData<List<StoreDisplayItem>>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var error: SingleLiveEvent<String> = SingleLiveEvent()

    init {
        getStoreResponse()
    }

    private fun getStoreResponse() {
        loading.value = true
        getCompositeDisposable().add(
            fortniteTrackerApi.getStore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    if (it.isSuccessful) {
                        val storeDisplayList = mutableListOf<StoreDisplayItem>()
                        val byCategory = it.body()!!.groupBy { storeItem -> storeItem.storeCategory }
                        val dailyItems = byCategory["BRDailyStorefront"]
                        val weeklyItems = byCategory["BRWeeklyStorefront"]
                        if (!dailyItems.isNullOrEmpty()) {
                            storeDisplayList.add(StoreHeaderItem("Daily Items"))
                            storeDisplayList.addAll(dailyItems.sortedBy { storeItem -> storeItem.vBucks })
                        }
                        if (!weeklyItems.isNullOrEmpty()) {
                            storeDisplayList.add(StoreHeaderItem("Featured Items"))
                            storeDisplayList.addAll(weeklyItems.sortedBy { storeItem -> storeItem.vBucks })
                        }
                        return@map storeDisplayList
                    } else {
                        error(it.message()!!)
                        return@map emptyList<StoreDisplayItem>()
                    }
                }
                .subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(response: List<StoreDisplayItem>) {
        loading.value = false
        storeItems.value = response
    }

    private fun handleError(throwable: Throwable) {
        error(throwable.message!!)
    }

    private fun error(message: String) {
        loading.value = false
        error.value = message
    }
}