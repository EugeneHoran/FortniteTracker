package fortnite.eugene.com.fortnitetracker.ui.account.stats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.stats.LifeTimeStat
import kotlinx.android.synthetic.main.recycler_stat_item.view.*

class StatsCombinedRecyclerAdapter : RecyclerView.Adapter<StatsCombinedRecyclerAdapter.CombinedViewHolder>() {

    private var combinedStatsItemList = mutableListOf<LifeTimeStat?>()
    fun setItems(combinedStatsItemList: List<LifeTimeStat?>) {
        this.combinedStatsItemList.clear()
        this.combinedStatsItemList.addAll(combinedStatsItemList)
        notifyDataSetChanged()
    }

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