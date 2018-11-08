package fortnite.eugene.com.fortnitetracker.ui.stats

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.polyak.iconswitch.IconSwitch
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.shared.OnAccountListener
import fortnite.eugene.com.fortnitetracker.ui.stats.match.MatchFragment
import fortnite.eugene.com.fortnitetracker.utils.Constants
import kotlinx.android.synthetic.main.fragment_stats.*
import java.lang.ref.WeakReference


private const val ARG_STATS = "param_stats"

class StatsMainFragment : Fragment(), IconSwitch.CheckedChangeListener {

    companion object {
        @JvmStatic
        fun newInstance(param1: AccountStats) =
            StatsMainFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_STATS, param1)
                }
            }
    }

    private val fragRef: WeakReference<StatsMainFragment> = WeakReference(this)
    private var listener: OnAccountListener? = null
    private lateinit var statsViewModel: StatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statsViewModel = ViewModelProviders.of(this)[StatsViewModel::class.java]
        if (savedInstanceState == null) {
            statsViewModel.setUserStats(arguments?.getParcelable(ARG_STATS)!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.navigationIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_search_24dp)
        toolbar.setNavigationOnClickListener {
            listener!!.onSearchClicked()
        }
        toolbar.inflateMenu(R.menu.menu_stats)
        val alertMenuItem = toolbar.menu.findItem(R.id.menu_toggle)
        val iconSwitch = alertMenuItem.actionView as IconSwitch
        iconSwitch.setCheckedChangeListener(this)
        handleViews(statsViewModel.toggleStatsMatch)
        observeUserStats()
    }


    override fun onCheckChanged(current: IconSwitch.Checked?) {
        if (current == IconSwitch.Checked.LEFT) {
            handleViews(Constants.TOGGLE_STATS)
        } else {
            handleViews(Constants.TOGGLE_MATCH_HISTORY)
        }
    }

    private fun handleViews(position: Int) {
        statsViewModel.toggleStatsMatch = position
        when (position) {
            0 -> {
                initStats()
            }
            1 -> {
                initHistory()
            }
        }
    }

    private fun initStats() {
        toolbar.title = "Stats"
        containerMatches.visibility = View.GONE
        tabs.visibility = View.VISIBLE
        toggleButtonLayout.visibility = View.VISIBLE
        pagerStats.visibility = View.VISIBLE

        app_bar.invalidate()
        if (pagerStats.adapter == null) {
            toggleButtonLayout.setToggled(toggleButtonLayout.toggles[statsViewModel.seasonToggle].id, true)
            toggleButtonLayout.onToggledListener = { toggle, _ ->
                statsViewModel.updateStatFragments(toggle.position)
            }
            pagerStats.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    app_bar.setExpanded(true, true)
                }
            })
            // There is too much going on so I am threading the child fragment creations
            pagerStats.post {
                if (fragRef.get() != null) {
                    if (fragRef.get()!!.pagerStats != null && fragRef.get()!!.tabs != null) {
                        fragRef.get()!!.pagerStats.adapter = StatsMainPagerAdapter(childFragmentManager)
                        fragRef.get()!!.tabs.setupWithViewPager(pagerStats)
                    }
                }
            }
        }
        setScrollingEnabled(true)
    }

    private fun initHistory() {
        toolbar.title = "Match History"
        containerMatches.visibility = View.VISIBLE
        tabs.visibility = View.GONE
        toggleButtonLayout.visibility = View.GONE
        pagerStats.visibility = View.GONE
        childFragmentManager.beginTransaction().apply {
            replace(
                R.id.containerMatches,
                MatchFragment.newInstance(statsViewModel.userStats.value!!.accountId!!)
            )
            commit()
        }
        setScrollingEnabled(false)
    }

    private fun observeUserStats() {
        statsViewModel.userStats.observe(this, Observer {
            if (it != null) {
                toolbar.subtitle = it.epicUserHandle
            }
        })
    }

    private fun setScrollingEnabled(isEnabled: Boolean) {
        val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags =
                if (isEnabled) AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS else 0

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAccountListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnAccountListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
