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
import kotlinx.android.synthetic.main.view_recycler_progress.*

class ChallengesFragment : BaseFragment<ChallengesViewModel>() {
    companion object {
        val TAG: String = ChallengesFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = ChallengesFragment()
    }

    override val layoutId: Int = R.layout.view_recycler_progress

    override val scrollFlags: Int? = Constants.SCROLL_FLAG_DEFAULT

    override fun getViewModel(): ChallengesViewModel {
        return ViewModelProviders.of(this).get(ChallengesViewModel::class.java)
    }

    private val adapter = ChallengesRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        recyclerView.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter
    }

    override fun activityCreated(savedInstanceState: Bundle?, viewModel: ChallengesViewModel) {
        initToolbar("Weekly Challenges", null, R.drawable.ic_trophy)
        observeChallenges(viewModel)
    }

    private fun observeChallenges(challengesViewModel: ChallengesViewModel) {
        showLoading()
        challengesViewModel.challenges.observe(this, Observer {
            if (it != null) {
                dismissLoading()
                adapter.setItems(it)
            }
        })
        challengesViewModel.error.observeSingleEvent(this, Observer {
            if (it != null) Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun dismissLoading() {
        pbLoadingView.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showLoading() {
        pbLoadingView.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    override fun onDetached() {
        getBaseActivity().onFragmentDetached(TAG)
    }
}
