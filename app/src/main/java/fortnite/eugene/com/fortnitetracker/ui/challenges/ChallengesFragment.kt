package fortnite.eugene.com.fortnitetracker.ui.challenges

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseFragment
import fortnite.eugene.com.fortnitetracker.utils.Constants
import kotlinx.android.synthetic.main.layout_recycler.*

class ChallengesFragment : BaseFragment() {
    companion object {
        val TAG: String = ChallengesFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = ChallengesFragment()
    }

    override val layoutId: Int = R.layout.layout_recycler
    override val scrollFlags: Int? = Constants.SCROLL_FLAG_DEFAULT

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar("Weekly Challenges", null, R.drawable.ic_trophy)
        observeChallenges(ViewModelProviders.of(activity!!).get(ChallengesViewModel::class.java))
    }

    private val adapter = ChallengesRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_container.isEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        recyclerView.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter
    }

    private fun observeChallenges(challengesViewModel: ChallengesViewModel) {
        challengesViewModel.challenges.observe(this, Observer {
            if (it != null) {
                adapter.setItemList(it)
            }
        })
        challengesViewModel.error.observeSingleEvent(this, Observer {
            if (it != null) Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
        })
    }
}
