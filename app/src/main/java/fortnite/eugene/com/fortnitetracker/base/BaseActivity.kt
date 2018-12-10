package fortnite.eugene.com.fortnitetracker.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.appbar.AppBarLayout
import fortnite.eugene.com.fortnitetracker.R

//  T : ViewDataBinding,
abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseFragment.Callback {
    private var viewDataBinding: T? = null

    fun getViewDataBinding(): T {
        return viewDataBinding!!
    }

    abstract val snackbarViewId: Int

    lateinit var snackbarView: View

    /**
     * @return layout resource id
     */
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
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
        snackbarView = findViewById(snackbarViewId)
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

        fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(
                view.windowToken,
                0
            )
        }
    }
}