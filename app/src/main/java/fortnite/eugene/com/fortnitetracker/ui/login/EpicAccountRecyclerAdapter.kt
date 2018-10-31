package fortnite.eugene.com.fortnitetracker.ui.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import kotlinx.android.synthetic.main.recycler_account_item.view.*

class EpicAccountRecyclerAdapter(
    val context: Context,
    private val epicAccountClickListener: EpicAccountClickListener
) : RecyclerView.Adapter<EpicAccountRecyclerAdapter.AccountViewHolder>() {

    interface EpicAccountClickListener {
        fun onAccountClicked(userAccount: UserAccount)
    }

    private var userAccountList = mutableListOf<UserAccount>()
    private val consoleImages = mapOf(1 to R.drawable.ic_xbox, 2 to R.drawable.ic_playstation, 3 to R.drawable.ic_pc)

    fun setItems(userAccountList: List<UserAccount>) {
        this.userAccountList.clear()
        this.userAccountList.addAll(userAccountList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: EpicAccountRecyclerAdapter.AccountViewHolder, position: Int) {
        val item = userAccountList[position]
        holder.image.setImageDrawable(ContextCompat.getDrawable(context, consoleImages[item.platformId]!!))
        holder.user.text = item.epicUserHandle
        holder.itemView.setOnClickListener {
            epicAccountClickListener.onAccountClicked(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpicAccountRecyclerAdapter.AccountViewHolder {
        return EpicAccountRecyclerAdapter.AccountViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recycler_account_item, parent, false
            )
        )
    }

    override fun getItemCount() = userAccountList.size
    class AccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.image!!
        val user = view.user!!
    }
}