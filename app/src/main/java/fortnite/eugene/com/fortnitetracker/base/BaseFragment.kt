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

abstract class BaseFragment : Fragment() {

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
        fun onUpdateScrollFlags(scrollFlags: Int?)
        fun onInflateMenu(menuId: Int?)
    }

    private var baseActivity: BaseActivity? = null

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    abstract val scrollFlags: Int?
    fun getBaseActivity(): BaseActivity = baseActivity!!

    abstract fun initData(savedInstanceState: Bundle?)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
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
        initData(savedInstanceState)
    }

    override fun onDetach() {
        if (baseActivity != null) {
            val toolbar = baseActivity!!.findViewById<Toolbar>(R.id.toolbar)
            toolbar.menu.clear()
        }
        baseActivity = null
        super.onDetach()
    }

    fun initToolbar(title: String? = null, subTitle: String? = null, navIcon: Int? = null) {
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
            getBaseActivity().getSnackbarView(),
            text,
            Snackbar.LENGTH_LONG
        )
    }
}