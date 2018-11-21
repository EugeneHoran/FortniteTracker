package fortnite.eugene.com.fortnitetracker.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats


abstract class BaseFragment<V : BaseViewModel> : Fragment() {

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
        fun updateScrollFlags(scrollFlags: Int?)

        fun onUserSignedIn(accountStats: AccountStats)
        fun onSearchClicked()
        fun inflateMenu(menuId: Int?)
    }

    private var baseActivity: BaseActivity<*>? = null
    private var viewModel: V? = null
    @get:LayoutRes
    abstract val layoutId: Int
    abstract val scrollFlags: Int?

    fun getBaseActivity(): BaseActivity<*> = baseActivity!!
    abstract fun getViewModel(): V
    abstract fun activityCreated(savedInstanceState: Bundle?, viewModel: V)
    abstract fun onDetached()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            this.baseActivity = context
            baseActivity!!.onFragmentAttached()
        }
    }

    override fun onDetach() {
        if (baseActivity != null) {
            val toolbar = baseActivity!!.findViewById<Toolbar>(R.id.toolbar)
            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(null)
                toolbar.menu.clear()
            }
        }
        onDetached()
        baseActivity = null
        super.onDetach()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActivity!!.updateScrollFlags(scrollFlags)
        viewModel = getViewModel()
        activityCreated(savedInstanceState, viewModel!!)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    fun initToolbar(
        title: String? = null,
        subTitle: String? = null,
        navIcon: Int? = null
    ) {
        if (baseActivity != null) {
            val toolbar = baseActivity!!.findViewById<Toolbar>(R.id.toolbar)
            if (toolbar != null) {
                toolbar.title = title
                toolbar.subtitle = subTitle
                if (navIcon == null) {
                    toolbar.navigationIcon = null
                } else {
                    toolbar.navigationIcon = ContextCompat.getDrawable(context!!, navIcon)
                }
            }
        }
    }
}