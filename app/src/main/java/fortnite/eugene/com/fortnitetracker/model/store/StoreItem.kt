package fortnite.eugene.com.fortnitetracker.model.store

import com.google.gson.annotations.SerializedName

data class StoreItem(
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("manifestId")
    val manifestId: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("rarity")
    val rarity: String?,
    @SerializedName("storeCategory")
    val storeCategory: String?,
    @SerializedName("vBucks")
    val vBucks: Int?
) : StoreDisplayItem() {

    fun getRarityColor(): String {
        return when (rarity) {
            "Handmade" -> "#008A09"
            "Sturdy" -> "#0163C6"
            "Quality" -> "#7F37D7"
            else -> "#008A09"
        }
    }
}