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
import fortnite.eugene.com.fortnitetracker.model.stats.LifeTimeStat
import kotlinx.android.synthetic.main.recycler_stat_item.view.*

class StatsSummaryPagerAdapter(val context: Context, private var combinedStatsItemList: List<LifeTimeStat?>) :
    PagerAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layout = inflater.inflate(R.layout.layout_recycler, container, false) as ViewGroup
        val recycler = layout.findViewById<RecyclerView>(R.id.recyclerView)
        layout.findViewById<SwipeRefreshLayout>(R.id.swipe_container).isEnabled = false
        recycler.layoutManager = GridLayoutManager(context, 2)
        recycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recycler.adapter = StatsSummaryRecyclerAdapter(combinedStatsItemList)
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


    /**
     * Recycler Adapter for stats summary
     */
    class StatsSummaryRecyclerAdapter(private var combinedStatsItemList: List<LifeTimeStat?>) :
        RecyclerView.Adapter<StatsSummaryRecyclerAdapter.CombinedViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, position: Int): CombinedViewHolder {
            return CombinedViewHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(R.layout.recycler_stat_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: CombinedViewHolder, position: Int) {
            holder.bind(combinedStatsItemList[position]!!)
        }

        override fun getItemCount(): Int = combinedStatsItemList.size

        class CombinedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(lifeTimeStat: LifeTimeStat) {
                itemView.itemPercentile.visibility = View.GONE
                itemView.percentileProgress.visibility = View.GONE
                itemView.itemRank.visibility = View.GONE
                itemView.title.text = lifeTimeStat.key!!
                itemView.displayText.text = lifeTimeStat.value!!
            }
        }
    }
}
