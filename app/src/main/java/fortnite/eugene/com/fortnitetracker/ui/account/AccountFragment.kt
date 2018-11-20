package fortnite.eugene.com.fortnitetracker.ui.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseFragment
import fortnite.eugene.com.fortnitetracker.inject.AppFactory
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats
import fortnite.eugene.com.fortnitetracker.ui.account.stats.StatsSummaryRecyclerAdapter
import fortnite.eugene.com.fortnitetracker.ui.account.stats.StatsViewModel
import fortnite.eugene.com.fortnitetracker.utils.Constants
import fortnite.eugene.com.togglebuttonlayout.ToggleButtonLayout
import kotlinx.android.synthetic.main.fragment_account.*


private const val ARG_STATS = "param_stats"

class AccountFragment : BaseFragment<StatsViewModel>() {
    companion object {
        val TAG: String = AccountFragment::class.java.simpleName
        @JvmStatic
        fun newInstance(param_stats: AccountStats) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_STATS, param_stats)
                }
            }
    }

    private lateinit var accountStats: AccountStats
    private lateinit var statsViewModel: StatsViewModel
    private lateinit var accountPagerAdapter: AccountPagerAdapter
    private lateinit var summaryRecyclerViewPager: StatsSummaryRecyclerViewPager

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
            R.drawable.ic_search_24dp
        )!!.setNavigationOnClickListener { getBaseActivity().onSearchClicked() }
        summaryRecyclerViewPager = StatsSummaryRecyclerViewPager(
            context!!,
            StatsSummaryRecyclerAdapter(accountStats.lifeTimeStats!!.reversed())
        )
        toggleButtonSeasons!!.visibility = View.VISIBLE
    }

    override fun activityCreated(savedInstanceState: Bundle?, viewModel: StatsViewModel) {
        statsViewModel = viewModel
        toggleButtonSeasons!!.setToggled(toggleButtonSeasons!!.toggles[statsViewModel.seasonToggle].id, true)
        toggleButtonSeasons!!.onToggledListener = { toggle, _ ->
            statsViewModel.updateStatFragments(toggle.position)
            handleViews()
        }
        accountPagerAdapter = AccountPagerAdapter(childFragmentManager)
        handleViews()
    }

    private fun handleViews() {
        when (statsViewModel.seasonToggle) {
            Constants.SEASON_COMBINED -> {
                getBaseActivity().updateScrollFlags(Constants.SCROLL_FLAG_TOGGLE)
                tabs!!.visibility = View.GONE
                tabs!!.setupWithViewPager(null)
                pagerStats.adapter = summaryRecyclerViewPager
            }
            Constants.SEASON_LIFETIME, Constants.SEASON_CURRENT -> {
                getBaseActivity().updateScrollFlags(Constants.SCROLL_FLAG_TOGGLE_TABS)
                tabs!!.visibility = View.VISIBLE
                if (pagerStats.adapter != accountPagerAdapter) {
                    pagerStats.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                        override fun onPageSelected(position: Int) {

                        }
                    })
                    pagerStats.adapter = accountPagerAdapter
                    tabs!!.setupWithViewPager(pagerStats)
                }
            }
        }
    }

    override fun onDetached() {
        toggleButtonSeasons!!.onToggledListener = null
        toggleButtonSeasons!!.visibility = View.GONE
        tabs!!.visibility = View.GONE
        toggleButtonSeasons = null
        tabs = null
        getBaseActivity().onFragmentDetached(TAG)
    }
}
