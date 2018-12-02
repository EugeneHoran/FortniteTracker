package fortnite.eugene.com.fortnitetracker.ui.stats

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
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
        return StatViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.recycler_stat_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        val item = displayStatsItemList[position]
        holder.title.text = item.getTitle()
        holder.displayText.text = item.getDisplayText()
        if (item.getItemPercentile() != null) {
            holder.percentileProgress.progress = item.getItemPercentile()!!.toInt()
            holder.itemPercentile.text =
                    "Top ${(100 - item.getItemPercentile()!!).toInt()}%, ${item.getItemPercentile()!!}th Percentile"
        } else {
            holder.percentileProgress.visibility = View.GONE
            holder.itemPercentile.visibility = View.GONE
        }

        if (item.getProgress() != null) {
            holder.progressImage.visibility = View.VISIBLE
            holder.progressText.visibility = View.VISIBLE
            holder.progressText.text = item.getDisplayProgress()
            holder.progressText.setTextColor(
                ContextCompat.getColor(
                    holder.progressText.context!!,
                    item.getTextColor()!!
                )
            )
            holder.progressImage.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.progressImage.context!!,
                    item.getArror()!!
                )
            )
        } else {
            holder.progressText.visibility = View.GONE
            holder.progressImage.visibility = View.GONE
        }
        if (item.getItemRank() != null) {
            holder.itemRank.text = item.getItemRank()
        } else {
            holder.itemRank.visibility = View.GONE
        }
    }

    override fun getItemCount() = displayStatsItemList.size

    class StatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.title!!
        val displayText = view.displayText!!
        val itemPercentile = view.itemPercentile!!
        val percentileProgress = view.percentileProgress!!
        val itemRank = view.itemRank!!
        val progressText = view.progressText!!
        val progressImage = view.progressImage!!
    }
}