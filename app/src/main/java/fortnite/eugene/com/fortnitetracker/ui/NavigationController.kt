package fortnite.eugene.com.fortnitetracker.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.login.EpicLoginFragment
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.ui.news.NewsFragment
import fortnite.eugene.com.fortnitetracker.ui.stats.StatsMainFragment


private const val FRAG_LOGIN = "frag_login"
private const val FRAG_STATS_MAIN = "frag_stats_main"
private const val FRAG_NEWS = "frag_news"

class NavigationController(savedInstanceState: Bundle?, var fm: FragmentManager, var loginViewModel: LoginViewModel) {
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
                navNewsFragment()
                return true
            }
            R.id.navigation_store -> {
                // TODO
                return true
            }
        }
        return false
    }

    fun navLoginFragment() {
        fm.beginTransaction().apply {
            replace(container, EpicLoginFragment(), FRAG_LOGIN)
            commit()
        }
    }

    fun navStatsFragment(accountStats: AccountStats) {
        fm.beginTransaction().apply {
            replace(container, StatsMainFragment.newInstance(accountStats), FRAG_STATS_MAIN)
            commit()
        }
    }

    fun navNewsFragment() {
        fm.beginTransaction().apply {
            replace(container, NewsFragment.newInstance(), FRAG_NEWS)
            commit()
        }
    }
}