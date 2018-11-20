package fortnite.eugene.com.fortnitetracker.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout


abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity(),
    BaseFragment.Callback {

    @get:LayoutRes
    abstract val layoutId: Int
    private lateinit var viewModel: V
    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        viewModel = getViewModel()
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    fun scrollingFlagsControl(viewsEnable: MutableMap<View, Boolean>, appBar: AppBarLayout) {
        appBar.setExpanded(true, true)
        val it = viewsEnable.iterator()
        while (it.hasNext()) {
            val pair = it.next() as Map.Entry<View, Boolean>
            val viewParams = pair.key.layoutParams as AppBarLayout.LayoutParams
            if (pair.value) {
                viewParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            } else {
                viewParams.scrollFlags = 0
            }
            it.remove()
        }
    }
}