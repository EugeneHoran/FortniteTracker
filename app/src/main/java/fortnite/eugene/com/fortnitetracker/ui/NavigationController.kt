package fortnite.eugene.com.fortnitetracker.ui

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionSet
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.challenges.ChallengesFragment
import fortnite.eugene.com.fortnitetracker.ui.history.MatchHistoryFragment
import fortnite.eugene.com.fortnitetracker.ui.login.EpicLoginFragment
import fortnite.eugene.com.fortnitetracker.ui.login.LoginViewModel
import fortnite.eugene.com.fortnitetracker.ui.stats.StatsParentFragment
import fortnite.eugene.com.fortnitetracker.ui.store.StoreFragment

class NavigationController(
    savedInstanceState: Bundle?,
    private var fm: FragmentManager,
    var loginViewModel: LoginViewModel,
    var lifecycleOwner: LifecycleOwner
) {

    private var container: Int = R.id.container

    init {
        if (savedInstanceState == null) {
            navLoginFragment()
            loginViewModel.userAccount.observe(lifecycleOwner, Observer {
                if (it != null) {
                    loginViewModel.getUserStats(it.platformName, it.displayName)
                } else {
                    navLoginFragment()
                }
                loginViewModel.userAccount.removeObservers(lifecycleOwner)
            })
        }
    }

    fun bottomNavController(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_search_player -> {
                navLoginFragment()
                return true
            }
            R.id.navigation_stats -> {
                navStatsFragment(loginViewModel.userStats.value!!)
                return true
            }
            R.id.navigation_history -> {
                MatchHistoryFragment.newInstance(
                    loginViewModel.userStats.value!!.accountId!!,
                    loginViewModel.userStats.value!!.getDisplayNameFormatted(),
                    loginViewModel.userStats.value!!.getLogoInt()
                ).replaceFragment(MatchHistoryFragment.TAG)
                return true
            }
            R.id.navigation_challenges -> {
                ChallengesFragment.newInstance().replaceFragment(ChallengesFragment.TAG)
                return true
            }
            R.id.navigation_item_shop -> {
                StoreFragment.newInstance().replaceFragment(StoreFragment.TAG)
                return true
            }
        }
        return false
    }

    fun navLoginFragment() {
        EpicLoginFragment.newInstance().replaceFragment(EpicLoginFragment.TAG)
    }

    fun navStatsFragment(accountStats: AccountStats) {
        StatsParentFragment.newInstance(accountStats).replaceFragment(StatsParentFragment.TAG)
    }

    private fun Fragment.replaceFragment(tag: String?) {
        val frag = this
        fm.beginTransaction().apply {
            enterTransition = TransitionSet().addTransition(Fade(Fade.IN).setInterpolator {
                (it - 0.5f) * 2
            }).addTransition(Slide(Gravity.BOTTOM))
            exitTransition = Fade(Fade.OUT)
            replace(container, frag, tag)
            commit()
        }
    }
}