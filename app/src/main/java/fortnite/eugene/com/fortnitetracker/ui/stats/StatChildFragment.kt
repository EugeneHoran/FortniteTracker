package fortnite.eugene.com.fortnitetracker.ui.stats


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseChildFragment
import fortnite.eugene.com.fortnitetracker.utils.Constants
import kotlinx.android.synthetic.main.layout_recycler.*

private const val ARG_TAB = "param_tab"

class StatChildFragment : BaseChildFragment<StatsViewModel>() {
    companion object {
        val TAG: String = StatChildFragment::class.java.simpleName
        @JvmStatic
        fun newInstance(tab: Int) =
            StatChildFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TAB, tab)
                }
            }
    }

    private var paramTab: Int? = null
    private var statRecyclerAdapter = StatRecyclerAdapter()
    override val layoutId: Int = R.layout.layout_recycler

    override fun getViewModel(): StatsViewModel = ViewModelProviders.of(parentFragment!!)[StatsViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramTab = it.getInt(ARG_TAB)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_container.isEnabled = false
        recyclerView.layoutManager = GridLayoutManager(context!!, 2)
        recyclerView.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = statRecyclerAdapter
    }

    override fun activityCreated(savedInstanceState: Bundle?, viewModel: StatsViewModel) {
        observeStatData(viewModel)
    }

    private fun observeStatData(statsViewModel: StatsViewModel) {
        when (paramTab) {
            Constants.TAB_SOLO -> {
                statsViewModel.soloStats.observe(this, Observer {
                    if (it != null) {
                        statRecyclerAdapter.setItems(it.getDisplayStats())
                    }
                })
            }
            Constants.TAB_DUO -> {
                statsViewModel.duoStats.observe(this, Observer {
                    if (it != null) {
                        statRecyclerAdapter.setItems(it.getDisplayStats())
                    }
                })
            }
            Constants.TAB_SQUADS -> {
                statsViewModel.squadStats.observe(this, Observer {
                    if (it != null) {
                        statRecyclerAdapter.setItems(it.getDisplayStats())
                    }
                })
            }
        }
    }
}
