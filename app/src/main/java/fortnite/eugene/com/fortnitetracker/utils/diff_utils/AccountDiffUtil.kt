package fortnite.eugene.com.fortnitetracker.utils.diff_utils

import androidx.recyclerview.widget.DiffUtil
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount

class AccountDiffUtil(private val newList: List<UserAccount>, private val oldList: List<UserAccount>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].accountId == newList[newItemPosition].accountId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].epicUserHandle == newList[newItemPosition].epicUserHandle
    }
}