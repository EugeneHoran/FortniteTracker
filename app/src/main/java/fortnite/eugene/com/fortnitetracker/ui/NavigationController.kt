package fortnite.eugene.com.fortnitetracker.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.account.AccountFragment
import fortnite.eugene.com.fortnitetracker.ui.challenges.ChallengesFragment
import fortnite.eugene.com.fortnitetracker.ui.login.EpicLoginFragment
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.ui.store.StoreFragment


private const val FRAG_LOGIN = "frag_login"
private const val FRAG_STATS_MAIN = "frag_stats_main"
private const val FRAG_NEWS = "frag_news"
private const val FRAG_STORE = "frag_store"

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
            R.id.navigation_account -> {
                if (loginViewModel.userStats.value == null) {
                    navLoginFragment()
                } else {
                    navStatsFragment(loginViewModel.userStats.value!!)
                }
                return true
            }
            R.id.navigation_news -> {
                navChallengesFragment()
                return true
            }
            R.id.navigation_store -> {
                navStoreFragment()
                return true
            }
        }
        return false
    }

    fun navLoginFragment() {
        fm.beginTransaction().apply {
            replace(container, EpicLoginFragment.newInstance(), FRAG_LOGIN)
            commit()
        }
    }

    fun navStatsFragment(accountStats: AccountStats) {
        fm.beginTransaction().apply {
            replace(container, AccountFragment.newInstance(accountStats), FRAG_STATS_MAIN)
            commit()
        }
    }

    fun navChallengesFragment() {
        fm.beginTransaction().apply {
            replace(container, ChallengesFragment.newInstance(), FRAG_NEWS)
            commit()
        }
    }

    fun navStoreFragment() {
        fm.beginTransaction().apply {
            replace(container, StoreFragment.newInstance(), FRAG_STORE)
        }.commit()
    }
}