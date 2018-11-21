package fortnite.eugene.com.fortnitetracker.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistory
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistoryHeader
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistoryItem
import fortnite.eugene.com.fortnitetracker.utils.formatToTimeAgo
import fortnite.eugene.com.fortnitetracker.utils.sticky_headers.StickyHeaders
import fortnite.eugene.com.fortnitetracker.utils.toDate
import kotlinx.android.synthetic.main.recycler_match_header.view.*
import kotlinx.android.synthetic.main.recycler_match_item.view.*

private const val HEADER = 0
private const val ITEM = 1
private const val LOADING = 2

private const val PROGRESS_LOADING = 1
private const val PROGRESS_NOT_LOADING = 0

class MatchHistoryRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaders {

    private var isLoadingCount: Int = 0
    private val itemList = mutableListOf<MatchHistoryItem>()

    init {
        isLoadingCount = PROGRESS_LOADING
        notifyDataSetChanged()
    }

    fun setLoading() {
        if (isLoadingCount != PROGRESS_LOADING) {
            isLoadingCount = PROGRESS_LOADING
            itemList.clear()
            notifyDataSetChanged()
        }
    }

    fun setItemList(items: List<MatchHistoryItem>) {
        isLoadingCount = PROGRESS_NOT_LOADING
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun isStickyHeader(position: Int): Boolean {
        return when {
            isLoadingCount == 1 -> false
            itemList[position] is MatchHistoryHeader -> true
            itemList[position] is MatchHistory -> false
            else -> false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isLoadingCount == 1 -> LOADING
            itemList[position] is MatchHistoryHeader -> HEADER
            itemList[position] is MatchHistory -> ITEM
            else -> -1
        }
    }

    override fun getItemCount(): Int = itemList.size + isLoadingCount

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> HeaderViewHolder(
                inflater.inflate(R.layout.recycler_match_header, parent, false)
            )
            ITEM -> MatchHistoryViewHolder(
                inflater.inflate(R.layout.recycler_match_item, parent, false)
            )
            else -> LoadingViewHolder(
                inflater.inflate(R.layout.view_progressbar, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(itemList[position] as MatchHistoryHeader)
        } else if (holder is MatchHistoryViewHolder) {
            holder.bind(itemList[position] as MatchHistory)
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MatchHistoryHeader) {
            itemView.date.text = item.getDateDisplay()
            itemView.matches.text = item.matches.toString()
            itemView.wins.text = item.wins.toString()
            itemView.kills.text = item.kills.toString()
            itemView.kd.text = item.getKd()
        }
    }

    class MatchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MatchHistory) {
            itemView.textMatchTimeAgo.text = item.dateCollected!!.toDate().formatToTimeAgo()
            itemView.matchMatches.text = item.matches!!.toString()
            itemView.matchWins.text = item.top1!!.toString()
            itemView.matchKills.text = item.kills!!.toString()
            itemView.matchKd.text = item.getKd()
            itemView.matchMode.text = item.getDisplayPlaylist()
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}