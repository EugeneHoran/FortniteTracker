package fortnite.eugene.com.fortnitetracker.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.ui.shared.OnAccountListener
import fortnite.eugene.com.fortnitetracker.utils.Constants
import kotlinx.android.synthetic.main.fragment_login.*

class EpicLoginFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = EpicLoginFragment()
    }

    private var listener: OnAccountListener? = null
    private lateinit var epicLoginViewModel: EpicLoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epicLoginViewModel = ViewModelProviders.of(this)[EpicLoginViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSearch.setOnClickListener {
            searchAccount()
        }
        observeUserData()
    }

    private fun observeUserData() {
        epicLoginViewModel.userAccountList.observe(this, Observer {
            if (it != null) {
                Toast.makeText(context!!, it.size.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        epicLoginViewModel.userStats.observe(this, Observer {
            if (it != null) {
                if (it.error == null) {
                    listener!!.onUserSignedIn(it)
                } else {
                    dismissLoading(it.error)
                }
            } else {
                dismissLoading("Error getting data")
            }
        })
        epicLoginViewModel.error.observeSingleEvent(activity!!, Observer {
            Toast.makeText(context!!, it!!, Toast.LENGTH_SHORT).show()
            dismissLoading(it)
        })
    }

    private fun showLoading() {
        linearLayoutHide.visibility = View.GONE
        pbLoading.visibility = View.VISIBLE
    }

    private fun dismissLoading(error: String?) {
        linearLayoutHide.visibility = View.VISIBLE
        pbLoading.visibility = View.GONE
        if (error != null) {
            Toast.makeText(context!!, error, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * View Helpers
     */

    private fun searchAccount() {
        editTextEpicUserName.hideKeyboard()
        if (getEpicName().isNullOrBlank()) {
            Toast.makeText(context, "Enter Epic Username", Toast.LENGTH_SHORT).show()
            return
        }
        showLoading()
        epicLoginViewModel.getUserStats(getPlatform()!!, getEpicName()!!)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun getEpicName(): String? {
        return editTextEpicUserName.text!!.trim().toString()
    }

    private fun getPlatform(): String? {
        return when (toggleButtonPlatform.getSelectedTogglePosition()) {
            Constants.PLATFORM_XBOX -> "xbl"
            Constants.PLATFORM_PS4 -> "psn"
            Constants.PLATFORM_PC -> "pc"
            else -> null
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAccountListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}
