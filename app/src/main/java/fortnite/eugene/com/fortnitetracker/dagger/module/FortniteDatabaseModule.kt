package fortnite.eugene.com.fortnitetracker.dagger.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import fortnite.eugene.com.fortnitetracker.dagger.AppScope
import fortnite.eugene.com.fortnitetracker.data.FortniteDatabase
import fortnite.eugene.com.fortnitetracker.data.dao.UserAccountDao

@Module(includes = [ContextModule::class])
class FortniteDatabaseModule {
    @Provides
    @AppScope
    fun database(context: Context): FortniteDatabase {
        return Room.databaseBuilder(context, FortniteDatabase::class.java, "fortnite.tracker.db").build()
    }

    @Provides
    @AppScope
    fun userAccountDao(database: FortniteDatabase): UserAccountDao {
        return database.getUserAccountDao()
    }
}