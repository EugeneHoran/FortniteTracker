package fortnite.eugene.com.fortnitetracker.ui.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fortnite.eugene.com.fortnitetracker.R
import kotlinx.android.synthetic.main.fragment_challenges.*

class ChallengesFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = ChallengesFragment()
    }

    private lateinit var challengesViewModel: ChallengesViewModel
    private val adapter = ChallengesRecyclerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        challengesViewModel = ViewModelProviders.of(this).get(ChallengesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenges, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerChallenges.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
        recyclerChallenges.adapter = adapter
        observeChallenges(challengesViewModel)
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
        pbLoading.visibility = View.INVISIBLE
        recyclerChallenges.visibility = View.VISIBLE
    }

    private fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        recyclerChallenges.visibility = View.INVISIBLE
    }
}
