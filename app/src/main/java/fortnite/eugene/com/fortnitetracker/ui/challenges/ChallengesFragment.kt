package fortnite.eugene.com.fortnitetracker.ui.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import fortnite.eugene.com.fortnitetracker.R
import kotlinx.android.synthetic.main.fragment_challenges.*

class ChallengesFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = ChallengesFragment()
    }

    private lateinit var challengesViewModel: ChallengesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        challengesViewModel = ViewModelProviders.of(this).get(ChallengesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenges, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pbLoading.visibility = View.VISIBLE
        Toast.makeText(context!!, "Still Working on it bitch", Toast.LENGTH_SHORT).show()
//        observeChallenges(challengesViewModel)
    }

    private fun observeChallenges(challengesViewModel: ChallengesViewModel) {
        challengesViewModel.challenges.observe(this, Observer {
            if (it != null) {
                Toast.makeText(context!!, it.items!![0]!!.getDisplayItemData().name, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context!!, "BAD", Toast.LENGTH_SHORT).show()
            }
        })

        challengesViewModel.error.observeSingleEvent(this, Observer {
            if (it != null) Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
        })
        challengesViewModel.showLoading.observeSingleEvent(this, Observer {
            if (it) {
                showLoading()
            } else {
                dismissLoading()
            }
        })
    }

    private fun dismissLoading() {
        pbLoading.visibility = View.GONE
        recyclerChallenges.visibility = View.VISIBLE
    }

    private fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        recyclerChallenges.visibility = View.GONE
    }
}
