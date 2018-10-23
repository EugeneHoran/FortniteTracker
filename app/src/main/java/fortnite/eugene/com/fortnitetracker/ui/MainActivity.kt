package fortnite.eugene.com.fortnitetracker.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import fortnite.eugene.com.fortnitetracker.App
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.data.service.StatsService
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var statsService: StatsService

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.graph.inject(this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        statsService.getUserStats("xbl", "eugeneisrambobox").observe(this, Observer {
            if (it.isSuccess) {
                Toast.makeText(this, "Success " + it.resource!!.accountId, Toast.LENGTH_SHORT).show()
            } else {
                Log.e("Testing", it.error!!.message)
                Toast.makeText(this, "Error " + it.error!!.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
