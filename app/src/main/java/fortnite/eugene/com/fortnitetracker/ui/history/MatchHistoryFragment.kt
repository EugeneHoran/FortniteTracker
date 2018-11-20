package fortnite.eugene.com.fortnitetracker.ui.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseFragment
import fortnite.eugene.com.fortnitetracker.inject.AppFactory
import fortnite.eugene.com.fortnitetracker.utils.Constants
import fortnite.eugene.com.fortnitetracker.utils.sticky_headers.StickyHeadersLinearLayoutManager
import kotlinx.android.synthetic.main.layout_recycler.*


private const val ARG_ACCOUNT_ID = "param_account_id"
private const val ARG_DISPLAY_NAME = "param_display_name"

class MatchHistoryFragment : BaseFragment<MatchHistoryViewModel>(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        val TAG: String = MatchHistoryFragment::class.java.simpleName
        @JvmStatic
        fun newInstance(accountId: String, displayName: String) = MatchHistoryFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_ACCOUNT_ID, accountId)
                putString(ARG_DISPLAY_NAME, displayName)
            }
        }
    }

    private var accountId: String? = null
    private var displayName: String? = null
    private lateinit var matchHistoryViewModel: MatchHistoryViewModel


    override val scrollFlags: Int? = Constants.SCROLL_FLAG_DEFAULT
    override val layoutId: Int = R.layout.layout_recycler

    override fun getViewModel(): MatchHistoryViewModel =
        ViewModelProviders.of(this, AppFactory(accountId!!)).get(MatchHistoryViewModel::class.java)

    override fun activityCreated(savedInstanceState: Bundle?, viewModel: MatchHistoryViewModel) {
        this.matchHistoryViewModel = viewModel
        observeMatchHistory(viewModel)
    }

    override fun onDetached() {
        getBaseActivity().onFragmentDetached(TAG)
    }

    private var matchHistoryAdapter = MatchHistoryRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountId = it.getString(ARG_ACCOUNT_ID)
            displayName = it.getString(ARG_DISPLAY_NAME)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(
            "Match History",
            displayName,
            R.drawable.ic_search_24dp
        )!!.setNavigationOnClickListener { getBaseActivity().onSearchClicked() }
        val mLinearLayoutManager = StickyHeadersLinearLayoutManager<MatchHistoryRecyclerAdapter>(
            activity!!,
            RecyclerView.VERTICAL,
            false
        )
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(context!!, RecyclerView.VERTICAL))
        recyclerView.adapter = matchHistoryAdapter
        swipe_container.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        swipe_container.isRefreshing = false
        matchHistoryViewModel.refreshData()
    }

    private fun observeMatchHistory(matchHistoryViewModel: MatchHistoryViewModel) {
        matchHistoryViewModel.matchHistory.observe(this, Observer {
            matchHistoryAdapter.setItemList(it!!)
        })
        matchHistoryViewModel.showLoading.observe(this, Observer {
            if (it != null) {
                if (it) {
                    swipe_container.isEnabled = false
                    matchHistoryAdapter.setLoading()
                } else {
                    swipe_container.isEnabled = true
                }
            }
        })
    }

}
