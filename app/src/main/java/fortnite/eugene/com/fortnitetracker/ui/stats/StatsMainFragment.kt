package fortnite.eugene.com.fortnitetracker.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import fortnite.eugene.com.fortnitetracker.R
import kotlinx.android.synthetic.main.fragment_stats.*


class StatsMainFragment : Fragment(), Toolbar.OnMenuItemClickListener {
    companion object {
        @JvmStatic
        fun newInstance() = StatsMainFragment()
    }

    private lateinit var statsViewModel: StatsViewModel
    private lateinit var statsPagerAdapter: StatsMainPagerAdapter
    private val consoleImages = mapOf(1 to R.drawable.ic_xbox, 2 to R.drawable.ic_playstation, 3 to R.drawable.ic_pc)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statsViewModel = ViewModelProviders.of(activity!!)[StatsViewModel::class.java]
        statsPagerAdapter = StatsMainPagerAdapter(childFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.inflateMenu(R.menu.menu_stats)
        toolbar.setOnMenuItemClickListener(this)
        pager.adapter = statsPagerAdapter
        tabs.setupWithViewPager(pager)
        pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                app_bar.setExpanded(true, true)
            }
        })
        toggleButtonLayout.setToggled(toggleButtonLayout.toggles[statsViewModel.seasonToggle].id, true)
        toggleButtonLayout.onToggledListener = { toggle, _ ->
            statsViewModel.updateStatFragments(toggle.position)
        }
        // TODO remove and add search fragment
        getUserStats("xbl", "eugeneisrambobox")
    }

    private fun getUserStats(platform: String, epicUserHandle: String) {
        statsViewModel.getUserStats(platform, epicUserHandle).observe(this, Observer {
            if (it != null) {
                toolbar.title = it.epicUserHandle
                toolbar.navigationIcon = ContextCompat.getDrawable(context!!, consoleImages[it.platformId]!!)
            }
        })
        statsViewModel.error.observeSingleEvent(this, Observer {
            if (it != null) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_search -> {
                getUserStats("xbl", "robinsoncanolis")
            }
        }
        return true
    }


}
