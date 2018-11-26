package fortnite.eugene.com.fortnitetracker.ui.stats

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseFragment
import fortnite.eugene.com.fortnitetracker.inject.AppFactory
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.utils.Constants
import fortnite.eugene.com.togglebuttonlayout.ToggleButtonLayout
import kotlinx.android.synthetic.main.fragment_account.*


private const val ARG_STATS = "param_stats"

class StatsParentFragment : BaseFragment<StatsViewModel>(), StatsSummaryPagerAdapter.SummaryCallback {
    companion object {
        val TAG: String = StatsParentFragment::class.java.simpleName
        @JvmStatic
        fun newInstance(param_stats: AccountStats) =
            StatsParentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_STATS, param_stats)
                }
            }
    }

    private lateinit var accountStats: AccountStats
    private lateinit var statsViewModel: StatsViewModel
    private lateinit var statsPagerAdapter: StatsPagerAdapter
    private lateinit var summaryRecyclerViewPager: StatsSummaryPagerAdapter

    private var toggleButtonSeasons: ToggleButtonLayout? = null
    private var tabs: TabLayout? = null

    override val layoutId: Int = R.layout.fragment_account
    override val scrollFlags: Int? = null

    override fun getViewModel(): StatsViewModel = ViewModelProviders.of(this, AppFactory(accountStats))
        .get(StatsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountStats = it.getParcelable(ARG_STATS)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toggleButtonSeasons = getBaseActivity().findViewById(R.id.toggleButtonSeasons)
        tabs = getBaseActivity().findViewById(R.id.tabs)
        initToolbar(
            getString(R.string.stats),
            accountStats.epicUserHandle,
            accountStats.getLogoInt()
        )

        getBaseActivity().onInflateMenu(R.menu.menu_search)
        summaryRecyclerViewPager = StatsSummaryPagerAdapter(context!!, accountStats.getSummaryData(), this)
        toggleButtonSeasons!!.visibility = View.VISIBLE
    }

    override fun initData(savedInstanceState: Bundle?, viewModel: StatsViewModel) {
        statsViewModel = viewModel
        toggleButtonSeasons!!.setToggled(toggleButtonSeasons!!.toggles[statsViewModel.seasonToggle].id, true)
        toggleButtonSeasons!!.onToggledListener = { toggle, _ ->
            statsViewModel.updateStatFragments(toggle.position)
            handleViews()
        }
        statsPagerAdapter = StatsPagerAdapter(childFragmentManager)
        handleViews()
    }

    override fun onChartItemSelected(position: Int) {
        toggleButtonSeasons!!.setToggled(toggleButtonSeasons!!.toggles[Constants.SEASON_LIFETIME].id, true)
        statsViewModel.updateStatFragments(Constants.SEASON_LIFETIME)
        handleViews()
        pagerStats.currentItem = position
    }

    private fun handleViews() {
        when (statsViewModel.seasonToggle) {
            Constants.SEASON_COMBINED -> {
                getBaseActivity().onUpdateScrollFlags(Constants.SCROLL_FLAG_TOGGLE)
                tabs!!.visibility = View.GONE
                tabs!!.setupWithViewPager(null)
                pagerStats.adapter = summaryRecyclerViewPager
                getBaseActivity().onInflateMenu(R.menu.menu_search)
            }
            Constants.SEASON_LIFETIME, Constants.SEASON_CURRENT -> {
                getBaseActivity().onUpdateScrollFlags(Constants.SCROLL_FLAG_TOGGLE_TABS)
                tabs!!.visibility = View.VISIBLE
                if (pagerStats.adapter != statsPagerAdapter) {
                    pagerStats.adapter = statsPagerAdapter
                    tabs!!.setupWithViewPager(pagerStats)
                }
            }
        }
    }

    override fun onDetach() {
        toggleButtonSeasons!!.onToggledListener = null
        toggleButtonSeasons!!.visibility = View.GONE
        tabs!!.visibility = View.GONE
        toggleButtonSeasons = null
        tabs = null
        super.onDetach()
    }
}
