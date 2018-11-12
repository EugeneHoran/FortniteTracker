package fortnite.eugene.com.fortnitetracker.ui.challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.challenges.ChallengeDisplayItem
import kotlinx.android.synthetic.main.recycler_challenge_item.view.*

class ChallengesRecyclerAdapter : RecyclerView.Adapter<ChallengesRecyclerAdapter.ChallengeViewHolder>() {
    private val itemList = mutableListOf<ChallengeDisplayItem>()

    fun setItems(itemList: List<ChallengeDisplayItem>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        return ChallengeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_challenge_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ChallengeDisplayItem) {
            Glide.with(itemView.context!!).load(item.rewardPictureUrl).into(itemView.imageReward)
            itemView.textChallengeName.text = item.name
            itemView.textChallengeTotal.text = item.questsTotal + " / " + item.questsCompleted
            itemView.textChallengeReward.text = item.rewardName

        }
    }
}