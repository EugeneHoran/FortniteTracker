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
import fortnite.eugene.com.fortnitetracker.ui.stats.StatsMainFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var statsService: StatsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.graph.inject(this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().replace(R.id.container, StatsMainFragment.newInstance()).commit()

//        statsService.getUserStats("xbl", "eugeneisrambobox").observe(this, Observer {
//            if (it.isSuccess) {
//                if (it.resource!!.error != null) {
//                    Toast.makeText(this, "Error not null", Toast.LENGTH_SHORT)
//                        .show()
//                } else {
//                    Toast.makeText(this, "Error null", Toast.LENGTH_SHORT)
//                        .show()
//                }
////                Toast.makeText(this, "Success " + it.resource!!.stats!!.lifetimeSolo!!.kd!!.value, Toast.LENGTH_SHORT)
////                    .show()
//            } else {
//                Log.e("Testing", it.error!!.message)
//                Toast.makeText(this, "Error " + it.error!!.message, Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
//                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
//                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
//                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
