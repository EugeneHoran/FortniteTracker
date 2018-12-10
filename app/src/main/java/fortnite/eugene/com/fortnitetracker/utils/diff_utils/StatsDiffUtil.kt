package fortnite.eugene.com.fortnitetracker.utils.diff_utils

import androidx.recyclerview.widget.DiffUtil
import fortnite.eugene.com.fortnitetracker.model.stats.DisplayStatsItem

class StatsDiffUtil(private val newList: List<DisplayStatsItem>, private val oldList: List<DisplayStatsItem>) :
        DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getTitle() == newList[newItemPosition].getTitle()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getDisplayText() == newList[newItemPosition].getDisplayText()
    }
}