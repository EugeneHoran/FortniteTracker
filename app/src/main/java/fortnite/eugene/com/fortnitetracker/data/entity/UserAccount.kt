package fortnite.eugene.com.fortnitetracker.data.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserAccounts")
data class UserAccount(
    @PrimaryKey @NonNull
    var accountId: String,
    var epicUserHandle: String,
    var platformId: Int,
    var platformName: String,
    var platformNameLong: String,
    var timestamp: Long,
    var displayName: String
)