package fortnite.eugene.com.fortnitetracker.ui.stats

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.Ref
import org.jetbrains.anko.coroutines.experimental.asReference


private const val ARG_PARAM1 = "param1"

class StatsMainFragment : Fragment(), Toolbar.OnMenuItemClickListener {
//    companion object {
//        @JvmStatic
//        fun newInstance() = StatsMainFragment()
//    }

    companion object {
        @JvmStatic
        fun newInstance(param1: AccountStats) =
            StatsMainFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }


    private lateinit var statsViewModel: StatsViewModel
    private lateinit var statsPagerAdapter: StatsMainPagerAdapter
    private val consoleImages = mapOf(1 to R.drawable.ic_xbox, 2 to R.drawable.ic_playstation, 3 to R.drawable.ic_pc)
    private lateinit var accountStats: AccountStats

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statsPagerAdapter = StatsMainPagerAdapter(childFragmentManager)
        arguments?.let {
            accountStats = it.getParcelable(ARG_PARAM1)!!
        }
        statsViewModel = ViewModelProviders.of(activity!!)[StatsViewModel::class.java]
        statsViewModel.setUserStats(accountStats)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.inflateMenu(R.menu.menu_stats)
        toolbar.setOnMenuItemClickListener(this)
        toggleButtonLayout.setToggled(toggleButtonLayout.toggles[statsViewModel.seasonToggle].id, true)
        toggleButtonLayout.onToggledListener = { toggle, _ ->
            statsViewModel.updateStatFragments(toggle.position)
        }
        pagerStats.adapter = statsPagerAdapter
        tabs.setupWithViewPager(pagerStats)
        pagerStats.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                app_bar.setExpanded(true, true)
            }
        })
        getUserStats()
        if (savedInstanceState == null) {
            val ref: Ref<StatsMainFragment> = this.asReference()
            async(UI) {
                ref().loadPager()
            }
        }
    }

    private fun loadPager() {
        pagerStats.adapter = statsPagerAdapter
        tabs.setupWithViewPager(pagerStats)
        pagerStats.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                app_bar.setExpanded(true, true)
            }
        })
    }

    private fun getUserStats() {
        statsViewModel.userStats.observe(this, Observer {
            if (it != null) {
                toolbar.title = it.epicUserHandle
                toolbar.navigationIcon = ContextCompat.getDrawable(context!!, consoleImages[it.platformId]!!)
                Handler().postDelayed({
                    statsViewModel.updateStatFragments(0)
                }, 2000)
            }
        })
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_search -> {
                //todo
            }
        }
        return true
    }

}
