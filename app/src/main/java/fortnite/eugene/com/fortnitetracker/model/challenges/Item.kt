package fortnite.eugene.com.fortnitetracker.model.challenges

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("metadata")
    val metadata: List<Metadata?>?
) {

    fun getDisplayItemData(): ChallengeDisplayItem {
        val data = getMappedData()
        return ChallengeDisplayItem(
            data["name"],
            data["questsCompleted"],
            data["questsTotal"],
            data["rewardPictureUrl"],
            data["rewardName"]
        )
    }

    private fun getMappedData(): HashMap<String, String> {
        val mapping = HashMap<String, String>()
        metadata!!.forEach {
            when (it!!.key!!) {
                "name",
                "questsCompleted",
                "questsTotal",
                "rewardPictureUrl",
                "rewardName"
                -> mapping[it.key!!] = it.value!!
            }
        }
        return mapping
    }
}