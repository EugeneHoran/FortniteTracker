package fortnite.eugene.com.fortnitetracker.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val PROGRESS_LOADING = 1
        const val PROGRESS_NOT_LOADING = 0

        const val ERROR_NULL = 0
        const val ERROR_NOT_NULL = 1
    }

    var isLoadingCount: Int = PROGRESS_NOT_LOADING
    var isErrorCount: Int = ERROR_NULL
    val itemList = mutableListOf<T>()

    fun setItemList(items: List<T>) {
        isLoadingCount = PROGRESS_NOT_LOADING
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        isLoadingCount = PROGRESS_NOT_LOADING
        isErrorCount = ERROR_NULL
        itemList.clear()
        notifyDataSetChanged()
    }


    fun setError() {
        if (isErrorCount != ERROR_NOT_NULL) {
            isLoadingCount = PROGRESS_NOT_LOADING
            isErrorCount = ERROR_NOT_NULL
            itemList.clear()
            notifyDataSetChanged()
        }
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

    override fun getItemCount(): Int = itemList.size + isLoadingCount + isErrorCount

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {

        }
    }
}