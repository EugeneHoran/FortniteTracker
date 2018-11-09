package fortnite.eugene.com.fortnitetracker.model.challenges

import com.google.gson.annotations.SerializedName

data class Challenges(
    @SerializedName("items")
    val items: List<Item?>?
)