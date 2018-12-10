package fortnite.eugene.com.fortnitetracker.utils

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class BottomNavigationBehavior : CoordinatorLayout.Behavior<BottomNavigationView> {

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun layoutDependsOn(
            parent: CoordinatorLayout, child: BottomNavigationView,
            dependency: View
    ): Boolean {
        if (dependency is Snackbar.SnackbarLayout) {
            updateSnackbar(child, dependency)
        }
        return super.layoutDependsOn(parent, child, dependency)
    }

    private fun updateSnackbar(child: View, snackbarLayout: Snackbar.SnackbarLayout) {
        if (snackbarLayout.layoutParams is CoordinatorLayout.LayoutParams) {
            val params = snackbarLayout.layoutParams as CoordinatorLayout.LayoutParams
            params.anchorId = child.id
            params.anchorGravity = Gravity.BOTTOM
            params.gravity = Gravity.BOTTOM
            params.dodgeInsetEdges = Gravity.BOTTOM
            snackbarLayout.layoutParams = params
        }
    }

}