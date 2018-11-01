package fortnite.eugene.com.fortnitetracker.inject

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import fortnite.eugene.com.fortnitetracker.data.FortniteDatabase
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val db =
                Room.databaseBuilder(activity.applicationContext, FortniteDatabase::class.java, "fortnite.tracker.db")
                    .build()
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(db.getUserAccountDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}