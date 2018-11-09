package fortnite.eugene.com.fortnitetracker.ui.challenges

import androidx.lifecycle.MediatorLiveData
import fortnite.eugene.com.fortnitetracker.base.BaseViewModel
import fortnite.eugene.com.fortnitetracker.model.challenges.Challenges
import fortnite.eugene.com.fortnitetracker.network.FortniteTrackerApi
import fortnite.eugene.com.fortnitetracker.utils.SingleLiveEvent
import javax.inject.Inject

class ChallengesViewModel : BaseViewModel() {

    @Inject
    lateinit var fortniteTrackerApi: FortniteTrackerApi
    var challenges = MediatorLiveData<Challenges>()
    var error: SingleLiveEvent<String> = SingleLiveEvent()
    var showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    init {
        showLoading.value = true
        challenges.addSource(fortniteTrackerApi.getChallenges()) {
            if (it != null) {
                if (it.error != null) {
                    error.value = it.error!!.message
                } else {
                    challenges.value = it.resource
                }
            }
            showLoading.value = false
        }
    }
}