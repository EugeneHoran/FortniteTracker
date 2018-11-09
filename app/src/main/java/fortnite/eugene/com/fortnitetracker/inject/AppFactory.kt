package fortnite.eugene.com.fortnitetracker.inject

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import fortnite.eugene.com.fortnitetracker.data.FortniteDatabase
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.account.match_history.MatchHistoryViewModel
import fortnite.eugene.com.fortnitetracker.ui.account.stats.StatsViewModel
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel


class AppFactory : ViewModelProvider.NewInstanceFactory {
    private lateinit var activity: AppCompatActivity
    private lateinit var key: String
    private lateinit var accountStats: AccountStats

    constructor(activity: AppCompatActivity) {
        this.activity = activity
    }

    constructor(key: String) {
        this.key = key
    }

    constructor(accountStats: AccountStats) {
        this.accountStats = accountStats
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
        } else if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            return StatsViewModel(accountStats) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}