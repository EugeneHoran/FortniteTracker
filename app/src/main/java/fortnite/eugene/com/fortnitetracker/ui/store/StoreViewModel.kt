package fortnite.eugene.com.fortnitetracker.ui.store

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.lifecycle.MediatorLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.model.store.StoreDisplayItem
import fortnite.eugene.com.fortnitetracker.model.store.StoreHeaderItem
import fortnite.eugene.com.fortnitetracker.model.store.StoreItem
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import fortnite.eugene.com.fortnitetracker.utils.SingleLiveEvent
import javax.inject.Inject

class StoreViewModel : BaseViewModel() {
    @Inject
    lateinit var fortniteTrackerApi: FortniteTrackerApi

    var storeItems = MediatorLiveData<List<StoreDisplayItem>>()
    var error: SingleLiveEvent<String> = SingleLiveEvent()
    var showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    init {
        showLoading.value = true
        storeItems.addSource(fortniteTrackerApi.getStoreItemList()) {
            if (it != null) {
                if (it.error != null) {
                    error.value = it.error!!.message
                } else {
                    sortData(it.resource!!)
                }
            }
            showLoading.value = false
        }
    }

    @SuppressLint("StaticFieldLeak")
    fun sortData(items: List<StoreItem>) {
        object : AsyncTask<List<StoreItem>, Void, List<StoreDisplayItem>>() {
            override fun doInBackground(vararg params: List<StoreItem>?): List<StoreDisplayItem> {
                val storeDisplayList = mutableListOf<StoreDisplayItem>()
                val byCategory = params[0]!!.groupBy { it.storeCategory }
                val dailyItems = byCategory["BRDailyStorefront"]
                val weeklyItems = byCategory["BRWeeklyStorefront"]
                if (!dailyItems.isNullOrEmpty()) {
                    storeDisplayList.add(StoreHeaderItem("Daily Items"))
                    storeDisplayList.addAll(dailyItems.sortedByDescending {
                        it.vBucks
                    })
                }
                if (!weeklyItems.isNullOrEmpty()) {
                    storeDisplayList.add(StoreHeaderItem("Featured Items"))
                    storeDisplayList.addAll(weeklyItems.sortedByDescending {
                        it.vBucks
                    })
                }
                return storeDisplayList
            }

            override fun onPostExecute(result: List<StoreDisplayItem>?) {
                storeItems.value = result
            }
        }.execute(items)
    }
//
//    private fun getSortedData(items: List<StoreItem>): List<StoreDisplayItem> {
//        val storeDisplayList = mutableListOf<StoreDisplayItem>()
//        val byCategory = items.groupBy { it.storeCategory }
//        val dailyItems = byCategory["BRDailyStorefront"]
//        val weeklyItems = byCategory["BRWeeklyStorefront"]
//        if (!dailyItems.isNullOrEmpty()) {
//            storeDisplayList.add(StoreHeaderItem("Daily Items"))
//            storeDisplayList.addAll(dailyItems.sortedByDescending {
//                it.vBucks
//            })
//        }
//        if (!weeklyItems.isNullOrEmpty()) {
//            storeDisplayList.add(StoreHeaderItem("Featured Items"))
//            storeDisplayList.addAll(weeklyItems.sortedByDescending {
//                it.vBucks
//            })
//        }
//        return storeDisplayList
//    }
}