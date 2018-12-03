package fortnite.eugene.com.fortnitetracker.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import fortnite.eugene.com.fortnitetracker.utils.diff_utils.AccountDiffUtil
import kotlinx.android.synthetic.main.recycler_account_item.view.*

class EpicAccountRecyclerAdapter(
    private val epicAccountClickListener: EpicAccountClickListener
) : RecyclerView.Adapter<EpicAccountRecyclerAdapter.AccountViewHolder>() {

    interface EpicAccountClickListener {
        fun onAccountClicked(userAccount: UserAccount)
        fun onAccountDeleted(userAccount: UserAccount)
    }

    private var userAccountList = mutableListOf<UserAccount>()
    private val consoleImages = mapOf(1 to R.drawable.ic_xbox, 2 to R.drawable.ic_playstation, 3 to R.drawable.ic_pc)

    fun setItems(userAccountList: List<UserAccount>) {
        if (userAccountList.isEmpty()) {
            this.userAccountList.clear()
            this.userAccountList.addAll(userAccountList)
            notifyDataSetChanged()
        } else {
            val diffCallback = AccountDiffUtil(
                userAccountList,
                this.userAccountList
            )
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.userAccountList.clear()
            this.userAccountList.addAll(userAccountList)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    fun removeAt(position: Int) {
        epicAccountClickListener.onAccountDeleted(userAccountList[position])
    }

    override fun onBindViewHolder(holder: EpicAccountRecyclerAdapter.AccountViewHolder, position: Int) {
        val item = userAccountList[position]
        holder.image.setImageDrawable(ContextCompat.getDrawable(holder.image.context, consoleImages[item.platformId]!!))
        holder.user.text = item.displayName
        holder.itemView.setOnClickListener {
            epicAccountClickListener.onAccountClicked(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpicAccountRecyclerAdapter.AccountViewHolder {
        return EpicAccountRecyclerAdapter.AccountViewHolder(
            LayoutInflater.from(parent.context).inflate(
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