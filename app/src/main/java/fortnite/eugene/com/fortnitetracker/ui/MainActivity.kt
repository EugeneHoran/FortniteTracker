package fortnite.eugene.com.fortnitetracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.inject.ViewModelFactory
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.ui.shared.OnAccountListener
import kotlinx.android.synthetic.main.activity_main.*

private const val FRAG_LOGIN = "frag_login"
private const val FRAG_STATS_MAIN = "frag_stats_main"
private const val FRAG_NEWS = "frag_news"

class MainActivity : AppCompatActivity(), OnAccountListener {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var navigationController: NavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginViewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(LoginViewModel::class.java)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigationController = NavigationController(savedInstanceState, supportFragmentManager, loginViewModel)
    }

    override fun onUserSignedIn(accountStats: AccountStats) {
        navigationController.navStatsFragment(accountStats)
    }

    override fun onSearchClicked() {
        loginViewModel.userStats.value = null
        navigationController.navLoginFragment()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        return@OnNavigationItemSelectedListener navigationController.bottomNavController(it)
    }
}
