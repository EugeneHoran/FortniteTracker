package fortnite.eugene.com.fortnitetracker.ui.store

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.store.StoreDisplayItem
import fortnite.eugene.com.fortnitetracker.model.store.StoreHeaderItem
import fortnite.eugene.com.fortnitetracker.model.store.StoreItem
import kotlinx.android.synthetic.main.recycler_store_header.view.*
import kotlinx.android.synthetic.main.recycler_store_item.view.*

class StoreRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val HEADER = 0
        const val ITEM = 1
    }

    private val itemList = mutableListOf<StoreDisplayItem>()

    init {
        notifyDataSetChanged()
    }

    fun setItemList(items: List<StoreDisplayItem>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            itemList[position] is StoreHeaderItem -> HEADER
            itemList[position] is StoreItem -> ITEM
            else -> -1
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> HeaderViewHolder(
                inflater.inflate(R.layout.recycler_store_header, parent, false)
            )
            ITEM -> StoreItemViewHolder(
                inflater.inflate(R.layout.recycler_store_item, parent, false)
            )
            else -> StoreItemViewHolder(
                inflater.inflate(R.layout.recycler_store_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(itemList[position] as StoreHeaderItem)
        } else if (holder is StoreItemViewHolder) {
            holder.bind(itemList[position] as StoreItem)
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: StoreHeaderItem) {
            itemView.textStoreHeader.text = item.title
        }
    }

    class StoreItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: StoreItem) {
            Glide.with(itemView.context!!).load(item.imageUrl!!).into(itemView.imageViewStoreItem)
            itemView.textStoreTitle.text = item.name
            itemView.textStoreCost.text = "${item.vBucks!!} V-Bucks"
            itemView.cardStoreHolder.setCardBackgroundColor(Color.parseColor(item.getRarityColor()))
        }
    }
}