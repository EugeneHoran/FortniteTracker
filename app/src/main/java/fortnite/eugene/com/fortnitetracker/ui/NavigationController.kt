package fortnite.eugene.com.fortnitetracker.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.account.AccountFragment
import fortnite.eugene.com.fortnitetracker.ui.challenges.ChallengesFragment
import fortnite.eugene.com.fortnitetracker.ui.history.MatchHistoryFragment
import fortnite.eugene.com.fortnitetracker.ui.login.EpicLoginFragment
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.ui.store.StoreFragment

class NavigationController(
    savedInstanceState: Bundle?,
    private var fm: FragmentManager,
    var loginViewModel: LoginViewModel
) {

    private var container: Int = R.id.container

    init {
        if (savedInstanceState == null) {
            navLoginFragment()
        }
    }

    fun bottomNavController(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_stats -> {
                if (loginViewModel.userStats.value == null) {
                    navLoginFragment()
                } else {
                    navStatsFragment(loginViewModel.userStats.value!!)
                }
                return true
            }
            R.id.navigation_history -> {
                if (loginViewModel.userStats.value == null) {
                    navLoginFragment()
                } else {
                    navHistoryFragment(
                        loginViewModel.userStats.value!!.accountId!!,
                        loginViewModel.userStats.value!!.epicUserHandle!!
                    )
                }
                return true
            }
            R.id.navigation_challenges -> {
                fm.beginTransaction().apply {
                    replace(container, ChallengesFragment.newInstance(), ChallengesFragment.TAG)
                    commit()
                }
                return true
            }
            R.id.navigation_item_shop -> {
                fm.beginTransaction().apply {
                    replace(container, StoreFragment.newInstance(), StoreFragment.TAG)
                }.commit()
                return true
            }
        }
        return false
    }

    fun navLoginFragment() {
        fm.beginTransaction().apply {
            replace(container, EpicLoginFragment.newInstance(), EpicLoginFragment.TAG)
            commit()
        }
    }

    fun navStatsFragment(accountStats: AccountStats) {
        fm.beginTransaction().apply {
            replace(container, AccountFragment.newInstance(accountStats), AccountFragment.TAG)
            commit()
        }
    }

    fun navHistoryFragment(accountId: String, displayName: String) {
        fm.beginTransaction().apply {
            replace(container, MatchHistoryFragment.newInstance(accountId, displayName), MatchHistoryFragment.TAG)
            commit()
        }
    }
}