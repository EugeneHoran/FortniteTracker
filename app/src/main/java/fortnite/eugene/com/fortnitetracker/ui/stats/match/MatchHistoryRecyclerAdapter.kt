package fortnite.eugene.com.fortnitetracker.ui.stats.match

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistory
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistoryHeader
import fortnite.eugene.com.fortnitetracker.model.matches.MatchHistoryItem
import fortnite.eugene.com.fortnitetracker.utils.sticky_headers.StickyHeaders
import kotlinx.android.synthetic.main.recycler_match_header.view.*
import kotlinx.android.synthetic.main.recycler_match_item.view.*

private const val HEADER = 0
private const val ITEM = 1

class MatchHistoryRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaders {


    private val itemList = mutableListOf<MatchHistoryItem>()

    fun setItemList(items: List<MatchHistoryItem>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun isStickyHeader(position: Int): Boolean = itemList[position] is MatchHistoryHeader
    override fun getItemViewType(position: Int): Int = if (itemList[position] is MatchHistoryHeader) HEADER else ITEM
    override fun getItemCount(): Int = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == HEADER) {
            HeaderViewHolder(inflater.inflate(R.layout.recycler_match_header, parent, false))
        } else {
            MatchHistoryViewHolder(inflater.inflate(R.layout.recycler_match_item, parent, false))
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
            itemView.matchTitle.text = item.kills!!.toString()
        }
    }
}