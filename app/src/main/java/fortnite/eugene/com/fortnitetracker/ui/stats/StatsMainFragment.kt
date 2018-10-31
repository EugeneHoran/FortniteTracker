package fortnite.eugene.com.fortnitetracker.ui.stats

import android.content.Context
import android.os.Bundle
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
import fortnite.eugene.com.fortnitetracker.ui.shared.OnAccountListener
import kotlinx.android.synthetic.main.fragment_stats.*
import java.lang.ref.WeakReference


private const val ARG_PARAM1 = "param1"

class StatsMainFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    companion object {
        @JvmStatic
        fun newInstance(param1: AccountStats) =
            StatsMainFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }

    private val fragRef: WeakReference<StatsMainFragment> = WeakReference(this)

    private var listener: OnAccountListener? = null
    private lateinit var statsViewModel: StatsViewModel
    private val consoleImages = mapOf(1 to R.drawable.ic_xbox, 2 to R.drawable.ic_playstation, 3 to R.drawable.ic_pc)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statsViewModel = ViewModelProviders.of(this)[StatsViewModel::class.java]
        if (savedInstanceState == null) {
            statsViewModel.setUserStats(arguments?.getParcelable(ARG_PARAM1)!!)
        }

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
        pagerStats.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                app_bar.setExpanded(true, true)
            }
        })
        pagerStats.post {
            if (fragRef.get() != null) {
                if (fragRef.get()!!.pagerStats != null && fragRef.get()!!.tabs != null) {
                    fragRef.get()!!.pagerStats.adapter = StatsMainPagerAdapter(childFragmentManager)
                    fragRef.get()!!.tabs.setupWithViewPager(pagerStats)
                }
            }
        }
        getUserStats()
    }

    private fun getUserStats() {
        statsViewModel.userStats.observe(this, Observer {
            if (it != null) {
                toolbar.title = it.epicUserHandle
                toolbar.navigationIcon = ContextCompat.getDrawable(context!!, consoleImages[it.platformId]!!)
            }
        })
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_search -> {
                listener!!.onSearchClicked()
            }
        }
        return true
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
