package fortnite.eugene.com.fortnitetracker.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.inject.AppFactory
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.base.BaseActivity
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.utils.Constants
import kotlinx.android.synthetic.main.activity_main2.*


class MainActivity2 : BaseActivity<LoginViewModel>() {

    override val layoutId: Int = R.layout.activity_main2
    override fun getViewModel(): LoginViewModel {
        loginViewModel = ViewModelProviders.of(this, AppFactory(this)).get(LoginViewModel::class.java)
        return loginViewModel
    }

    private lateinit var navigationController: NavigationController
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation.setOnNavigationItemReselectedListener { }
        navigation.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener navigationController.bottomNavController(it)
        }
        navigationController = NavigationController(savedInstanceState, supportFragmentManager, loginViewModel)
    }

    override fun onUserSignedIn(accountStats: AccountStats) {
        if (navigation.selectedItemId == R.id.navigation_stats) {
            navigationController.navStatsFragment(accountStats)
        } else if (navigation.selectedItemId == R.id.navigation_history) {
            navigationController.navHistoryFragment(accountStats.accountId!!, accountStats.epicUserHandle!!)
        }
    }

    override fun onSearchClicked() {
        loginViewModel.userStats.value = null
        navigationController.navLoginFragment()
    }

    override fun updateScrollFlags(scrollFlags: Int?) {
        when (scrollFlags) {
            null -> return
            Constants.SCROLL_FLAG_DEFAULT ->
                scrollingFlagsControl(
                    mutableMapOf(toolbar to true, toggleButtonSeasons to false, tabs to false),
                    app_bar_main
                )
            Constants.SCROLL_FLAG_TOGGLE ->
                scrollingFlagsControl(
                    mutableMapOf(toolbar to true, toggleButtonSeasons to false, tabs to false),
                    app_bar_main
                )
            Constants.SCROLL_FLAG_TOGGLE_TABS ->
                scrollingFlagsControl(
                    mutableMapOf(toolbar to true, toggleButtonSeasons to true, tabs to false),
                    app_bar_main
                )
        }
    }
}