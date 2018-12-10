package fortnite.eugene.com.fortnitetracker.ui.challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseRecyclerAdapter
import fortnite.eugene.com.fortnitetracker.base.BaseViewHolder
import fortnite.eugene.com.fortnitetracker.model.challenges.ChallengeDisplayItem
import kotlinx.android.synthetic.main.recycler_challenge_item.view.*

class ChallengesRecyclerAdapter : BaseRecyclerAdapter<ChallengeDisplayItem>() {

    companion object {
        const val ITEM = 0
        const val LOADING = 2
    }

    init {
        setLoading()
    }

    override fun getItemViewType(position: Int): Int {
        return when (isLoadingCount) {
            1 -> LOADING
            else -> ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChallengeViewHolder) {
            holder.bind(itemList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM -> ChallengeViewHolder(
                inflater.inflate(R.layout.recycler_challenge_item, parent, false)
            )
            else -> LoadingViewHolder(
                inflater.inflate(R.layout.view_progressbar, parent, false)
            )
        }
    }

    class ChallengeViewHolder(itemView: View) : BaseViewHolder<ChallengeDisplayItem>(itemView) {
        override fun bind(item: ChallengeDisplayItem) {
            Glide.with(itemView.context!!).load(item.rewardPictureUrl).into(itemView.imageReward)
            itemView.textChallengeName.text = item.name
            itemView.textChallengeTotal.text = String.format("${item.questsTotal} / ${item.questsCompleted}")
            itemView.textChallengeReward.text = item.rewardName

        }
    }
}