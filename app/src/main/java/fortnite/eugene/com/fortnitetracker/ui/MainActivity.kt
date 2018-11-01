package fortnite.eugene.com.fortnitetracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.login.EpicLoginFragment
import fortnite.eugene.com.fortnitetracker.ui.shared.OnAccountListener
import fortnite.eugene.com.fortnitetracker.ui.stats.StatsMainFragment

class MainActivity : AppCompatActivity(), OnAccountListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().replace(R.id.container, EpicLoginFragment.newInstance()).commit()
    }

    override fun onUserSignedIn(accountStats: AccountStats) {
        supportFragmentManager.beginTransaction().replace(R.id.container, StatsMainFragment.newInstance(accountStats))
            .commit()
    }

    override fun onSearchClicked() {
        supportFragmentManager.beginTransaction().replace(R.id.container, EpicLoginFragment.newInstance()).commit()
    }
}
