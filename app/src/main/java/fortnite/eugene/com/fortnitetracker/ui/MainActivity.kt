package fortnite.eugene.com.fortnitetracker.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.inject.AppFactory
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnAccountListener {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var navigationController: NavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
        loginViewModel = ViewModelProviders.of(this, AppFactory(this)).get(LoginViewModel::class.java)
        navigation.setOnNavigationItemReselectedListener { }
        navigation.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener navigationController.bottomNavController(it)
        }
        navigationController = NavigationController(savedInstanceState, supportFragmentManager, loginViewModel)
    }

    override fun onUserSignedIn(accountStats: AccountStats) {
        navigationController.navStatsFragment(accountStats)
    }

    override fun onSearchClicked() {
        loginViewModel.userStats.value = null
        navigationController.navLoginFragment()
    }
}
