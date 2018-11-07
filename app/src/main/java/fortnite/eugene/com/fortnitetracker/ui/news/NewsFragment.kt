package fortnite.eugene.com.fortnitetracker.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fortnite.eugene.com.fortnitetracker.R
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pbLoading.visibility = View.VISIBLE
    }
}
