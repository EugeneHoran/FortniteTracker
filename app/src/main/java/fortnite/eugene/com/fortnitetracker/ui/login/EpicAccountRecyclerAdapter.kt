package fortnite.eugene.com.fortnitetracker.ui.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseViewHolder
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import fortnite.eugene.com.fortnitetracker.ui.login.EpicAccountRecyclerAdapter.AccountViewHolder
import fortnite.eugene.com.fortnitetracker.utils.diff_utils.AccountDiffUtil
import kotlinx.android.synthetic.main.recycler_account_item.view.*

class EpicAccountRecyclerAdapter(
    private val context: Context,
    private val epicAccountClickListener: OnAccountInteractionListener
) : RecyclerView.Adapter<AccountViewHolder>() {

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

    fun getItem(position: Int): UserAccount = userAccountList[position]
    override fun getItemCount() = userAccountList.size
    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(userAccountList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
        AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_account_item, parent, false))

    inner class AccountViewHolder(itemView: View) : BaseViewHolder<UserAccount>(itemView), View.OnClickListener {
        override fun bind(item: UserAccount) {
            itemView.image.setImageResource(consoleImages[item.platformId]!!)
            itemView.user.text = item.displayName
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) = epicAccountClickListener.accountClickLogin(userAccountList[adapterPosition])
    }
}