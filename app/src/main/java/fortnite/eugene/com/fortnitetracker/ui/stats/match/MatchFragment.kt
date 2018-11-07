package fortnite.eugene.com.fortnitetracker.ui.stats.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.utils.sticky_headers.StickyHeadersLinearLayoutManager
import kotlinx.android.synthetic.main.fragment_match.*


private const val ARG_PARAM1 = "param1"

class MatchFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(param1: String) = MatchFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }
        }
    }

    private var param1: String? = null
    private lateinit var matchHistoryViewModel: MatchHistoryViewModel

    private var adapter = MatchHistoryRecyclerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        matchHistoryViewModel = ViewModelProviders.of(this).get(MatchHistoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    //    private lateinit var layoutManager: StickyHeadersLinearLayoutManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mLinearLayoutManager = StickyHeadersLinearLayoutManager<MatchHistoryRecyclerAdapter>(activity!!)
        matchesRecycler.setHasFixedSize(true)
        matchesRecycler.layoutManager = mLinearLayoutManager
        matchesRecycler.adapter = adapter
        if (savedInstanceState == null) {
            matchHistoryViewModel.getMatchHistory(param1!!)
        }
        observeMatchHistory()
    }

    private fun observeMatchHistory() {
        matchHistoryViewModel.matchHistory.observe(this, Observer {
            adapter.setItemList(it!!)
        })
    }
}
