package fortnite.eugene.com.fortnitetracker.ui.account.matchhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.inject.AppFactory
import fortnite.eugene.com.fortnitetracker.utils.sticky_headers.StickyHeadersLinearLayoutManager
import kotlinx.android.synthetic.main.layout_recycler.*


private const val ARG_PARAM1 = "param1"

class MatchHistoryFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        @JvmStatic
        fun newInstance(param1: String) = MatchHistoryFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }
        }
    }

    private var param1: String? = null
    private lateinit var matchHistoryViewModel: MatchHistoryViewModel

    private var matchHistoryAdapter = MatchHistoryRecyclerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        matchHistoryViewModel = ViewModelProviders.of(this, AppFactory(param1!!)).get(MatchHistoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        observeMatchHistory()
    }

    override fun onRefresh() {
        swipe_container.isRefreshing = false
        matchHistoryViewModel.refreshData()
    }

    private fun observeMatchHistory() {
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
