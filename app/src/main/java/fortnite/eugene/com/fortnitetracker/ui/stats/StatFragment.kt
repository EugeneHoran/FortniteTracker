package fortnite.eugene.com.fortnitetracker.ui.stats


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.utils.Constants
import kotlinx.android.synthetic.main.fragment_stat.*

private const val ARG_TAB = "param_tab"

class StatFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(tab: Int) =
            StatFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TAB, tab)
                }
            }
    }

    private var paramTab: Int? = null
    private lateinit var statsViewModel: StatsViewModel
    private lateinit var statRecyclerAdapter: StatRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramTab = it.getInt(ARG_TAB)
        }
        statsViewModel = ViewModelProviders.of(parentFragment!!)[StatsViewModel::class.java]
        statRecyclerAdapter = StatRecyclerAdapter(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statRecycler.layoutManager = GridLayoutManager(context, 2)
        statRecycler.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
        statRecycler.adapter = statRecyclerAdapter
        observeStatData()
    }

    private fun observeStatData() {
        when (paramTab) {
            Constants.TAB_SOLO -> observeSoloStats()
            Constants.TAB_DUO -> observeDuoStats()
            Constants.TAB_SQUADS -> observeSquadStats()
        }
    }

    private fun observeSoloStats() {
        statsViewModel.soloStats.observe(this, Observer {
            if (it != null) {
                statRecyclerAdapter.setItems(it.getDisplayStats())
            }
        })
    }

    private fun observeDuoStats() {
        statsViewModel.duoStats.observe(this, Observer {
            if (it != null) {
                statRecyclerAdapter.setItems(it.getDisplayStats())
            }
        })
    }

    private fun observeSquadStats() {
        statsViewModel.squadStats.observe(this, Observer {
            if (it != null) {
                statRecyclerAdapter.setItems(it.getDisplayStats())
            }
        })
    }
}
