package fortnite.eugene.com.fortnitetracker.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseActivity
import fortnite.eugene.com.fortnitetracker.inject.AppFactory
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.utils.Constants
import kotlinx.android.synthetic.main.activity_main2.*


class MainActivity : BaseActivity<LoginViewModel>(), Toolbar.OnMenuItemClickListener {
    private lateinit var navigationController: NavigationController
    private lateinit var loginViewModel: LoginViewModel


    override val layoutId: Int = R.layout.activity_main2

    override fun getViewModel(): LoginViewModel {
        loginViewModel = ViewModelProviders.of(this, AppFactory(this)).get(LoginViewModel::class.java)
        return loginViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setOnMenuItemClickListener(this)
        navigation.setOnNavigationItemReselectedListener { }
        navigation.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener navigationController.bottomNavController(it)
        }
        navigationController = NavigationController(savedInstanceState, supportFragmentManager, loginViewModel, this)
        observeLoginStatus()
    }

    private fun observeLoginStatus() {
        loginViewModel.loginStatus.observe(this, Observer {
            if (it != null) {
                if (it) {
                    navigation.menu.clear()
                    navigation.inflateMenu(R.menu.navigation_logged_in)
                } else {
                    navigation.menu.clear()
                    navigation.inflateMenu(R.menu.navigation_logged_out)
                }
            }
        })
    }

    override fun onLogin(parameters: Any?) {
        navigationController.navStatsFragment(parameters as AccountStats)
    }

    override fun onLogout() {
        loginViewModel.loginStatus.value = false
        loginViewModel.userStats.value = null
        navigationController.navLoginFragment()
    }

    override fun onInflateMenu(menuId: Int?) {
        toolbar.menu.clear()
        if (menuId == null) {
            return
        }
        toolbar.inflateMenu(menuId)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_clear -> {
                loginViewModel.clearSearchHistory()
            }
            R.id.menu_search -> {
                onLogout()
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (loginViewModel.loginStatus.value == true) {
            if (navigation.selectedItemId == R.id.navigation_stats) {
                if (toggleButtonSeasons.getSelectedTogglePosition() != 0) {
                    toggleButtonSeasons.setToggled(toggleButtonSeasons!!.toggles[Constants.SEASON_COMBINED].id, true)
                    return
                }
            } else {
                navigation.selectedItemId = R.id.navigation_stats
                return
            }
        } else {
            if (navigation.selectedItemId != R.id.navigation_search_player) {
                navigation.selectedItemId = R.id.navigation_search_player
                return
            }
        }
        super.onBackPressed()
    }

    override fun onUpdateScrollFlags(scrollFlags: Int?) {
        when (scrollFlags) {
            null -> return
            Constants.SCROLL_FLAG_NONE ->
                scrollingFlagsControl(
                    mutableMapOf(toolbar to false, toggleButtonSeasons to false, tabs to false),
                    app_bar_main
                )
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