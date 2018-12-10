package fortnite.eugene.com.fortnitetracker.ui.stats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseViewHolder
import fortnite.eugene.com.fortnitetracker.model.stats.DisplayStatsItem
import fortnite.eugene.com.fortnitetracker.utils.diff_utils.StatsDiffUtil
import kotlinx.android.synthetic.main.recycler_stat_item.view.*

class StatRecyclerAdapter : RecyclerView.Adapter<StatRecyclerAdapter.StatViewHolder>() {

    private var displayStatsItemList = mutableListOf<DisplayStatsItem>()

    fun setItems(statsItemList: List<DisplayStatsItem>) {
        if (displayStatsItemList.isEmpty()) {
            this.displayStatsItemList.clear()
            this.displayStatsItemList.addAll(statsItemList)
            notifyDataSetChanged()
        } else {
            val diffCallback = StatsDiffUtil(
                this.displayStatsItemList,
                statsItemList
            )
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.displayStatsItemList.clear()
            this.displayStatsItemList.addAll(statsItemList)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        return StatRecyclerAdapter.StatViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.recycler_stat_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        holder.bind(displayStatsItemList[position])
    }

    override fun getItemCount() = displayStatsItemList.size

    class StatViewHolder(itemView: View) : BaseViewHolder<DisplayStatsItem>(itemView) {
        override fun bind(item: DisplayStatsItem) {
            itemView.title.text = item.getTitle()
            itemView.displayText.text = item.getDisplayText()
            if (item.getItemPercentile() != null) {
                itemView.percentileProgress.progress = item.getItemPercentile()!!.toInt()
                itemView.itemPercentile.text =
                        "Top ${(100 - item.getItemPercentile()!!).toInt()}%, ${item.getItemPercentile()!!}th Percentile"
            } else {
                itemView.percentileProgress.visibility = View.GONE
                itemView.itemPercentile.visibility = View.GONE
            }

            if (item.getProgress() != null) {
                itemView.progressImage.visibility = View.VISIBLE
                itemView.progressText.visibility = View.VISIBLE
                itemView.progressText.text = item.getDisplayProgress()
                itemView.progressText.setTextColor(
                    ContextCompat.getColor(
                        itemView.progressText.context!!,
                        item.getTextColor()!!
                    )
                )
                itemView.progressImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.progressImage.context!!,
                        item.getArror()!!
                    )
                )
            } else {
                itemView.progressText.visibility = View.GONE
                itemView.progressImage.visibility = View.GONE
            }
            if (item.getItemRank() != null) {
                itemView.itemRank.text = item.getItemRank()
            } else {
                itemView.itemRank.visibility = View.GONE
            }
        }
    }
}