package fortnite.eugene.com.fortnitetracker.ui.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.ui.account.stats.StatsSummaryRecyclerAdapter


class StatsSummaryRecyclerViewPager(val context: Context, val adapter: StatsSummaryRecyclerAdapter) : PagerAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layout = inflater.inflate(R.layout.layout_recycler, container, false) as ViewGroup
        val recycler = layout.findViewById<RecyclerView>(R.id.recyclerView)
        layout.findViewById<SwipeRefreshLayout>(R.id.swipe_container).isEnabled = false
        recycler.layoutManager = GridLayoutManager(context, 2)
        recycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recycler.adapter = adapter
        container.addView(layout)
        return layout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int = 1

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }
}
