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
private const val ARG_DISPLAY_LOGO = "param_display_logo"

class MatchHistoryFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        val TAG: String = MatchHistoryFragment::class.java.simpleName
        @JvmStatic
        fun newInstance(accountId: String, displayName: String, displayLogo: Int) = MatchHistoryFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_ACCOUNT_ID, accountId)
                putString(ARG_DISPLAY_NAME, displayName)
                putInt(ARG_DISPLAY_LOGO, displayLogo)
            }
        }
    }

    private var accountId: String? = null
    private var displayName: String? = null
    private var logoInt: Int? = null
    private lateinit var matchHistoryViewModel: MatchHistoryViewModel


    override val scrollFlags: Int? = Constants.SCROLL_FLAG_DEFAULT
    override val layoutId: Int = R.layout.layout_recycler


    private var matchHistoryAdapter = MatchHistoryRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountId = it.getString(ARG_ACCOUNT_ID)
            displayName = it.getString(ARG_DISPLAY_NAME)
            logoInt = it.getInt(ARG_DISPLAY_LOGO)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBaseActivity().onInflateMenu(R.menu.menu_search)
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

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar(displayName, "Match History", logoInt)
        matchHistoryViewModel =
                ViewModelProviders.of(this, AppFactory(accountId!!)).get(MatchHistoryViewModel::class.java)
        observeMatchHistory(matchHistoryViewModel)
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
