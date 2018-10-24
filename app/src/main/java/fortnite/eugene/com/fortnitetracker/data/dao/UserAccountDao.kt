package fortnite.eugene.com.fortnitetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount

@Dao
abstract class UserAccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: UserAccount)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(user: UserAccount)

    @Delete
    abstract fun delete(user: UserAccount)

    @Query("SELECT * from UserAccounts ORDER BY timestamp DESC LIMIT 1")
    abstract fun getLatestUser(): LiveData<UserAccount>

    @Query("SELECT * FROM UserAccounts ORDER BY timestamp DESC")
    abstract fun getUserList(): LiveData<List<UserAccount>>
}