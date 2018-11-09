package fortnite.eugene.com.fortnitetracker.model.challenges

import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
)