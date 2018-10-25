package fortnite.eugene.com.fortnitetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import fortnite.eugene.com.fortnitetracker.utils.BaseDao

@Dao
abstract class UserAccountDao : BaseDao<UserAccount> {
    @Query("SELECT * from UserAccounts ORDER BY timestamp DESC LIMIT 1")
    abstract fun getLatestUser(): LiveData<UserAccount>

    @Query("SELECT * FROM UserAccounts ORDER BY timestamp DESC")
    abstract fun getUserList(): LiveData<List<UserAccount>>
}