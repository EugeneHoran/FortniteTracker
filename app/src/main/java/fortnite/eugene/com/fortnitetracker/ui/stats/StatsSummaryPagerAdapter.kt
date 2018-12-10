package fortnite.eugene.com.fortnitetracker.ui.stats

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
import fortnite.eugene.com.fortnitetracker.base.BaseViewHolder
import fortnite.eugene.com.fortnitetracker.model.stats.ChartDataItem
import fortnite.eugene.com.fortnitetracker.model.stats.LifeTimeStat
import kotlinx.android.synthetic.main.recycler_pie_chart_item.view.*
import kotlinx.android.synthetic.main.recycler_stat_item.view.*
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener
import lecho.lib.hellocharts.model.SliceValue

class StatsSummaryPagerAdapter(
    val context: Context,
    private var combinedStatsItemList: List<Any>,
    var summaryCallback: SummaryCallback
) :
    PagerAdapter() {

    interface SummaryCallback {
        fun onChartItemSelected(position: Int)
    }


    companion object {
        const val CHART_ITEM = 0
        const val SUMMARY_ITEM = 1
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layout = inflater.inflate(R.layout.layout_recycler, container, false) as ViewGroup
        val recycler = layout.findViewById<RecyclerView>(R.id.recyclerView)
        layout.findViewById<SwipeRefreshLayout>(R.id.swipe_container).isEnabled = false
        val adapter = StatsSummaryRecyclerAdapter2(combinedStatsItemList, summaryCallback)
        val glm = GridLayoutManager(context, 2)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    CHART_ITEM -> 2
                    SUMMARY_ITEM -> 1
                    else -> 0
                }
            }
        }
        recycler.setHasFixedSize(true)
        recycler.layoutManager = glm
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

    /**
     * RecyclerView Adapter
     */
    class StatsSummaryRecyclerAdapter2(private var itemList: List<Any?>, var summaryCallback: SummaryCallback) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemViewType(position: Int): Int {
            return when {
                itemList[position] is ChartDataItem -> CHART_ITEM
                itemList[position] is LifeTimeStat -> SUMMARY_ITEM
                else -> -1
            }
        }

        override fun getItemCount(): Int = itemList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                CHART_ITEM ->
                    PieChartViewHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.recycler_pie_chart_item, parent, false)
                        , summaryCallback
                    )
                SUMMARY_ITEM ->
                    CombinedViewHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.recycler_stat_item, parent, false)
                    )
                else -> CombinedViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.recycler_stat_item, parent, false)
                )

            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is PieChartViewHolder) {
                holder.bind(itemList[position]!! as ChartDataItem)
            } else if (holder is CombinedViewHolder) {
                holder.bind(itemList[position]!! as LifeTimeStat)
            }
        }

        class CombinedViewHolder(itemView: View) : BaseViewHolder<LifeTimeStat>(itemView) {
            override fun bind(item: LifeTimeStat) {
                itemView.itemPercentile.visibility = View.GONE
                itemView.percentileProgress.visibility = View.GONE
                itemView.itemRank.visibility = View.GONE
                itemView.title.text = item.key!!
                itemView.displayText.text = item.value!!
            }
        }

        class PieChartViewHolder(itemView: View, private var summaryCallback: SummaryCallback) :
            BaseViewHolder<ChartDataItem>(itemView),
            PieChartOnValueSelectListener {

            override fun bind(item: ChartDataItem) {
                itemView.pieChartView.isChartRotationEnabled = false
                itemView.pieChartView.pieChartData = item.pieChartData
                itemView.pieChartView.onValueTouchListener = this
                itemView.txtSolo.text = String.format("${item.soloMatched} Solo matches")
                itemView.txtDuo.text = String.format("${item.duoMatches} Duo matches")
                itemView.txtSquads.text = String.format("${item.squadMatches} Squad matches")
            }

            override fun onValueSelected(p0: Int, p1: SliceValue?) {
                summaryCallback.onChartItemSelected(p0)
            }

            override fun onValueDeselected() {}
        }

    }
}
