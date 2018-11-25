package fortnite.eugene.com.fortnitetracker.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val PROGRESS_LOADING = 1
        const val PROGRESS_NOT_LOADING = 0
    }

    var isLoadingCount: Int = PROGRESS_NOT_LOADING
    val itemList = mutableListOf<T>()

    fun setItemList(items: List<T>) {
        isLoadingCount = PROGRESS_NOT_LOADING
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun setLoading() {
        if (isLoadingCount != PROGRESS_LOADING) {
            isLoadingCount = PROGRESS_LOADING
            itemList.clear()
            notifyDataSetChanged()
        }
    }

    fun isLoading(): Boolean {
        return isLoadingCount == PROGRESS_LOADING
    }

    override fun getItemCount(): Int = itemList.size + isLoadingCount

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}