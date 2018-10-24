package fortnite.eugene.com.fortnitetracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import fortnite.eugene.com.fortnitetracker.data.dao.UserAccountDao
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount

@Database(entities = [UserAccount::class], version = 1, exportSchema = false)
abstract class FortniteDatabase : RoomDatabase() {
    abstract fun getUserAccountDao(): UserAccountDao
}