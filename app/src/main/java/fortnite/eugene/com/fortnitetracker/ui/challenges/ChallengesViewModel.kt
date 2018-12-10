package fortnite.eugene.com.fortnitetracker.ui.challenges

import androidx.lifecycle.MediatorLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.model.challenges.ChallengeDisplayItem
import fortnite.eugene.com.fortnitetracker.model.challenges.Item
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import fortnite.eugene.com.fortnitetracker.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChallengesViewModel : BaseViewModel() {

    @Inject
    lateinit var fortniteTrackerApi: FortniteTrackerApi
    var challenges = MediatorLiveData<List<ChallengeDisplayItem>>()
    var error: SingleLiveEvent<String> = SingleLiveEvent()
    var showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    init {
        showLoading.value = true
        getCompositeDisposable().add(
            fortniteTrackerApi.getChallenge()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    if (it.isSuccessful) {
                        val itemList = mutableListOf<ChallengeDisplayItem>()
                        it.body()!!.items!!.forEach { displayItem: Item? ->
                            itemList.add(displayItem!!.getDisplayItemData())
                        }
                        return@map itemList
                    } else {
                        return@map emptyList<ChallengeDisplayItem>()
                    }
                }.subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(response: List<ChallengeDisplayItem>) {
        challenges.value = response
        showLoading.value = false
    }

    private fun handleError(throwable: Throwable) {
        error.value = throwable.message
        showLoading.value = false
    }
}