package fortnite.eugene.com.fortnitetracker.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseChildFragment<V : BaseViewModel<*>> : Fragment() {
    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getViewModel(): V
    abstract fun activityCreated(savedInstanceState: Bundle?, viewModel: V)
    private var viewModel: V? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel()
        activityCreated(savedInstanceState, viewModel!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }
}