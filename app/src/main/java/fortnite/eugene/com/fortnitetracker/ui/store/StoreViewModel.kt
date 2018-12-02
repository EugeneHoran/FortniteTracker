package fortnite.eugene.com.fortnitetracker.ui.store

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.model.store.StoreDisplayItem
import fortnite.eugene.com.fortnitetracker.model.store.StoreHeaderItem
import fortnite.eugene.com.fortnitetracker.model.store.StoreItem
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
        getStoreResposnse()
    }

    fun getStoreResposnse() {
        loading.value = true
        getCompositeDisposable().add(
            fortniteTrackerApi.getStore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(response: List<StoreItem>) {
        sortData(response)
    }

    private fun handleError(throwable: Throwable) {
        loading.value = false
        error.value = throwable.message
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
                loading.value = false
                storeItems.value = result
            }
        }.execute(items)
    }
}