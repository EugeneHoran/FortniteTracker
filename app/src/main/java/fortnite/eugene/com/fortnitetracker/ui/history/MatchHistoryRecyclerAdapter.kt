package fortnite.eugene.com.fortnitetracker.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseRecyclerAdapter
import fortnite.eugene.com.fortnitetracker.base.BaseViewHolder
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


class MatchHistoryRecyclerAdapter : BaseRecyclerAdapter<MatchHistoryItem>(), StickyHeaders {

    init {
        setLoading()
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

    class HeaderViewHolder(itemView: View) : BaseViewHolder<MatchHistoryHeader>(itemView) {
        override fun bind(item: MatchHistoryHeader) {
            itemView.date.text = item.getDateDisplay()
            itemView.matches.text = item.matches.toString()
            itemView.wins.text = item.wins.toString()
            itemView.kills.text = item.kills.toString()
            itemView.kd.text = item.getKd()
        }
    }

    class MatchHistoryViewHolder(itemView: View) : BaseViewHolder<MatchHistory>(itemView) {
        override fun bind(item: MatchHistory) {
            itemView.textMatchTimeAgo.text = item.dateCollected!!.toDate().formatToTimeAgo()
            itemView.matchMatches.text = item.matches!!.toString()
            itemView.matchWins.text = item.top1!!.toString()
            itemView.matchKills.text = item.kills!!.toString()
            itemView.matchKd.text = item.getKd()
            itemView.matchMode.text = item.getDisplayPlaylist()
        }
    }

}