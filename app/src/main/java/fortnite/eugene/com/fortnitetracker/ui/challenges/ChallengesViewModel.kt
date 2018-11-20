package fortnite.eugene.com.fortnitetracker.ui.challenges

import androidx.lifecycle.MediatorLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.model.challenges.ChallengeDisplayItem
import fortnite.eugene.com.fortnitetracker.model.challenges.Item
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import fortnite.eugene.com.fortnitetracker.utils.SingleLiveEvent
import javax.inject.Inject

class ChallengesViewModel : BaseViewModel() {

    @Inject
    lateinit var fortniteTrackerApi: FortniteTrackerApi
    var challenges = MediatorLiveData<List<ChallengeDisplayItem>>()
    var error: SingleLiveEvent<String> = SingleLiveEvent()
    var showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    init {
        showLoading.value = true
        challenges.addSource(fortniteTrackerApi.getChallenges()) {
            if (it != null) {
                if (it.error != null) {
                    error.value = it.error!!.message
                } else {
                    val itemList = mutableListOf<ChallengeDisplayItem>()
                    it.resource!!.items!!.forEach { displayItem: Item? ->
                        itemList.add(displayItem!!.getDisplayItemData())
                    }
                    challenges.value = itemList
                }
            }
            showLoading.value = false
        }
    }
}