package fortnite.eugene.com.fortnitetracker.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import fortnite.eugene.com.fortnitetracker.R

//  T : ViewDataBinding,
abstract class BaseActivity : AppCompatActivity(), BaseFragment.Callback {
    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * @return snackbar resource id
     */
    abstract val snackbarViewId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
    }

    fun getSnackbarView(): View {
        return findViewById(snackbarViewId)
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