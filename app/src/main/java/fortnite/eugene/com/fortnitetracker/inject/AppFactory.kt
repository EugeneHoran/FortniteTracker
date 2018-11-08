package fortnite.eugene.com.fortnitetracker.inject

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import fortnite.eugene.com.fortnitetracker.data.FortniteDatabase
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.ui.stats.match.MatchHistoryViewModel


class AppFactory : ViewModelProvider.NewInstanceFactory {
    private lateinit var activity: AppCompatActivity
    private lateinit var key: String

    constructor(activity: AppCompatActivity) {
        this.activity = activity
    }

    constructor(key: String) {
        this.key = key
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val db = Room.databaseBuilder(
                activity.applicationContext, FortniteDatabase::class.java, "fortnite.tracker.db"
            ).build()
            return LoginViewModel(db.getUserAccountDao()) as T
        } else if (modelClass.isAssignableFrom(MatchHistoryViewModel::class.java)) {
            return MatchHistoryViewModel(key) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}