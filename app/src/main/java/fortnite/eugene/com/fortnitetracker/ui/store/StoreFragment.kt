package fortnite.eugene.com.fortnitetracker.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import fortnite.eugene.com.fortnitetracker.R
import kotlinx.android.synthetic.main.fragment_store.*


class StoreFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = StoreFragment()
    }

    private lateinit var storeViewModel: StoreViewModel
    private val storeAdapter = StoreRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeViewModel = ViewModelProviders.of(this).get(StoreViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val glm = GridLayoutManager(context, 2)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (storeAdapter.getItemViewType(position)) {
                    StoreRecyclerAdapter.ITEM -> 1
                    StoreRecyclerAdapter.HEADER -> 2
                    else -> 0
                }
            }
        }
        recyclerStore.layoutManager = glm
        recyclerStore.adapter = storeAdapter
        observeData(storeViewModel)
    }

    private fun observeData(storeViewModel: StoreViewModel) {
        showLoading()
        storeViewModel.storeItems.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                dismissLoading()
                storeAdapter.setItemList(it)
            }
        })
        storeViewModel.error.observeSingleEvent(this, Observer {
            if (it.isNotBlank()) Toast.makeText(context!!, it!!, Toast.LENGTH_SHORT).show()
            dismissLoading()
        })
    }


    private fun dismissLoading() {
        pbLoadingStore.visibility = View.INVISIBLE
        recyclerStore.visibility = View.VISIBLE
    }

    private fun showLoading() {
        pbLoadingStore.visibility = View.VISIBLE
        recyclerStore.visibility = View.INVISIBLE
    }
}
