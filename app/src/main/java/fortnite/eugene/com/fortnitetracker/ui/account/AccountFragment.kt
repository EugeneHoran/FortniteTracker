package fortnite.eugene.com.fortnitetracker.ui.account

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.polyak.iconswitch.IconSwitch
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.inject.AppFactory
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.account.match_history.MatchHistoryFragment
import fortnite.eugene.com.fortnitetracker.ui.account.stats.StatsCombinedRecyclerAdapter
import fortnite.eugene.com.fortnitetracker.ui.account.stats.StatsViewModel
import fortnite.eugene.com.fortnitetracker.ui.shared.OnAccountListener
import fortnite.eugene.com.fortnitetracker.utils.Constants
import kotlinx.android.synthetic.main.fragment_account.*


private const val ARG_STATS = "param_stats"
private const val FRAG_MATCH_HISTORY = "frag_match_history"
private const val TOOLBAR_TITLE_STATS = "Stats"
private const val TOOLBAR_TITLE_MATCH_HISTORY = "Match History"

class AccountFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(param_stats: AccountStats) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_STATS, param_stats)
                }
            }
    }

    /**
     * Delaying some views
     */
    private val initViewsLiveData = MutableLiveData<Boolean>()
    private var viewHandler = Handler()
    private val initViewsRunnable = Runnable {
        initViewsLiveData.value = true
    }

    private var listener: OnAccountListener? = null
    private lateinit var statsViewModel: StatsViewModel
    private val statsCombinedRecyclerAdapter = StatsCombinedRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statsViewModel = ViewModelProviders.of(
            this,
            AppFactory(accountStats = arguments?.getParcelable(ARG_STATS)!!)
        ).get(StatsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener {
            listener!!.onSearchClicked()
        }
        toolbar.inflateMenu(R.menu.menu_stats)
        toolbar.subtitle = statsViewModel.accountStats.epicUserHandle
        val iconSwitch = toolbar.menu.findItem(R.id.menu_toggle).actionView as IconSwitch
        iconSwitch.setCheckedChangeListener {
            when (it!!) {
                IconSwitch.Checked.LEFT -> handleViews(Constants.TOGGLE_STATS)
                IconSwitch.Checked.RIGHT -> handleViews(Constants.TOGGLE_MATCH_HISTORY)
            }
        }
        recyclerCombined.adapter = statsCombinedRecyclerAdapter
        recyclerCombined.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
        toggleButtonSeasons.setToggled(toggleButtonSeasons.toggles[statsViewModel.seasonToggle].id, true)
        toggleButtonSeasons.onToggledListener = { toggle, _ ->
            statsViewModel.updateStatFragments(toggle.position)
            handleViews(Constants.TOGGLE_STATS)
        }
        statsCombinedRecyclerAdapter.setItems(statsViewModel.accountStats.lifeTimeStats!!.reversed())
        initViewsLiveData.observe(this, Observer {
            handleViews(statsViewModel.toggleStatsMatch)
        })
        viewHandler.post(initViewsRunnable)
    }

    private fun handleViews(position: Int) {
        statsViewModel.toggleStatsMatch = position
        when (position) {
            Constants.TOGGLE_STATS -> {
                toolbar.title = TOOLBAR_TITLE_STATS
                if (childFragmentManager.findFragmentByTag(FRAG_MATCH_HISTORY) != null) {
                    childFragmentManager.beginTransaction().apply {
                        remove(childFragmentManager.findFragmentByTag(FRAG_MATCH_HISTORY) as MatchHistoryFragment)
                        commit()
                    }
                }
                when (statsViewModel.seasonToggle) {
                    Constants.SEASON_COMBINED -> {
                        tabs.visibility = View.GONE
                        containerMatches.visibility = View.GONE
                        toggleButtonSeasons.visibility = View.VISIBLE
                        pagerStats.visibility = View.GONE
                        recyclerCombined.visibility = View.VISIBLE
                        setScrollingEnabled(false)
                    }
                    Constants.SEASON_LIFETIME, Constants.SEASON_CURRENT -> {
                        containerMatches.visibility = View.GONE
                        toggleButtonSeasons.visibility = View.VISIBLE
                        tabs.visibility = View.VISIBLE
                        pagerStats.visibility = View.VISIBLE
                        recyclerCombined.visibility = View.INVISIBLE
                        if (pagerStats.adapter == null) {
                            pagerStats.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                                override fun onPageSelected(position: Int) {
                                    app_bar.setExpanded(true, true)
                                }
                            })
                            pagerStats.adapter = AccountPagerAdapter(childFragmentManager)
                            tabs.setupWithViewPager(pagerStats)
                        }
                        setScrollingEnabled(true)
                    }
                }
            }
            Constants.TOGGLE_MATCH_HISTORY -> {
                toolbar.title = TOOLBAR_TITLE_MATCH_HISTORY
                containerMatches.visibility = View.VISIBLE
                tabs.visibility = View.GONE
                toggleButtonSeasons.visibility = View.GONE
                pagerStats.visibility = View.GONE
                recyclerCombined.visibility = View.GONE
                childFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.containerMatches,
                        MatchHistoryFragment.newInstance(statsViewModel.accountStats.accountId!!),
                        FRAG_MATCH_HISTORY
                    )
                    commit()
                }
                setScrollingEnabled(false)
            }
        }
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
        viewHandler.removeCallbacks(initViewsRunnable)
    }
}
