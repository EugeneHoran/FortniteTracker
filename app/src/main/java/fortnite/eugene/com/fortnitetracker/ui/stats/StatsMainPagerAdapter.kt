package fortnite.eugene.com.fortnitetracker.ui.stats

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fortnite.eugene.com.fortnitetracker.utils.Constants

class StatsMainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val tabTitles = arrayOf("Solo", "Duo", "Squads")
    private var fragments: Array<StatFragment?>? = null

    override fun getItem(position: Int): Fragment {
        if (fragments != null && fragments!!.size > position && fragments!![position] != null) {
            return fragments!![position]!!
        }
        if (fragments == null) {
            fragments = arrayOfNulls(count)
        }
        when (position) {
            Constants.TAB_SOLO -> fragments!![Constants.TAB_SOLO] = StatFragment.newInstance(position)
            Constants.TAB_DUO -> fragments!![Constants.TAB_DUO] = StatFragment.newInstance(position)
            Constants.TAB_SQUADS -> fragments!![Constants.TAB_SQUADS] = StatFragment.newInstance(position)
        }
        return fragments!![position]!!
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? = tabTitles[position]
}