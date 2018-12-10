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
import com.google.android.material.snackbar.Snackbar
import com.ravikoradiya.library.CenterTitle
import fortnite.eugene.com.fortnitetracker.R

abstract class BaseFragment<V : BaseViewModel<*>> : Fragment() {

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
        fun onUpdateScrollFlags(scrollFlags: Int?)
        fun onInflateMenu(menuId: Int?)
        fun onLogin(parameters: Any? = null)
        fun onLogout()
    }

    private var baseActivity: BaseActivity<*, *>? = null

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    private var viewModel: V? = null

    abstract val scrollFlags: Int?
    fun getBaseActivity(): BaseActivity<*, *> = baseActivity!!
    abstract fun getViewModel(): V
    abstract fun initData(savedInstanceState: Bundle?, viewModel: V)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.baseActivity = context
            baseActivity!!.onFragmentAttached()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActivity!!.onUpdateScrollFlags(scrollFlags)
        viewModel = getViewModel()
        initData(savedInstanceState, viewModel!!)
    }

    override fun onDetach() {
        if (baseActivity != null) {
            val toolbar = baseActivity!!.findViewById<Toolbar>(R.id.toolbar)
            toolbar.menu.clear()
        }
        baseActivity = null
        super.onDetach()
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
            CenterTitle.centerTitle(toolbar, true)
        }
    }

    fun snackbar(text: String): Snackbar {
        return Snackbar.make(
            getBaseActivity().snackbarView,
            text,
            Snackbar.LENGTH_LONG
        )
    }
}