package fortnite.eugene.com.fortnitetracker.ui.store

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseFragment
import fortnite.eugene.com.fortnitetracker.utils.Constants
import kotlinx.android.synthetic.main.layout_recycler.*

class StoreFragment : BaseFragment<StoreViewModel>() {
    companion object {
        val TAG: String = StoreFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = StoreFragment()
    }

    override val layoutId: Int = R.layout.layout_recycler
    override val scrollFlags: Int? = Constants.SCROLL_FLAG_DEFAULT
    override fun getViewModel() = ViewModelProviders.of(this).get(StoreViewModel::class.java)

    private val storeAdapter = StoreRecyclerAdapter()

    override fun initData(savedInstanceState: Bundle?, viewModel: StoreViewModel) {
        initToolbar(getString(R.string.item_shop), null, R.drawable.ic_store)
        observeData(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_container.isEnabled = false
        val glm = GridLayoutManager(context, 2)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (storeAdapter.getItemViewType(position)) {
                    StoreRecyclerAdapter.ITEM -> 1
                    StoreRecyclerAdapter.HEADER -> 2
                    StoreRecyclerAdapter.LOADING -> 2
                    else -> 0
                }
            }
        }
        recyclerView.layoutManager = glm
        recyclerView.adapter = storeAdapter
    }

    private fun observeData(storeViewModel: StoreViewModel) {
        storeViewModel.storeItems.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                storeAdapter.setItemList(it)
            }
        })
        storeViewModel.error.observeSingleEvent(this, Observer {
            if (it.isNotBlank()) Toast.makeText(context!!, it!!, Toast.LENGTH_SHORT).show()
        })
    }
}
