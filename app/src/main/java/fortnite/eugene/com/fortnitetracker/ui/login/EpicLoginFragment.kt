package fortnite.eugene.com.fortnitetracker.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import fortnite.eugene.com.fortnitetracker.ui.OnAccountListener
import fortnite.eugene.com.fortnitetracker.utils.Constants
import kotlinx.android.synthetic.main.fragment_login.*

class EpicLoginFragment : Fragment(),
    EpicAccountRecyclerAdapter.EpicAccountClickListener,
    Toolbar.OnMenuItemClickListener {

    companion object {
        @JvmStatic
        fun newInstance() = EpicLoginFragment()
    }

    private var listener: OnAccountListener? = null
    private lateinit var loginViewModel: LoginViewModel
    private var epicAccountRecyclerAdapter = EpicAccountRecyclerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setOnMenuItemClickListener(this)
        btnSearch.setOnClickListener {
            searchAccount()
        }
        recyclerAccount.adapter = epicAccountRecyclerAdapter
        observeUserData()
    }

    private fun searchAccount() {
        editTextEpicUserName.hideKeyboard()
        if (getEpicName().isNullOrBlank()) {
            Toast.makeText(context, getString(R.string.enter_epic_username), Toast.LENGTH_SHORT).show()
            return
        }
        loginViewModel.getUserStats(getPlatform()!!, getEpicName()!!)
    }

    override fun onAccountClicked(userAccount: UserAccount) {
        loginViewModel.getUserStats(userAccount.platformName, userAccount.epicUserHandle)
    }

    private fun observeUserData() {
        loginViewModel.userAccountList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.isNotEmpty()) toolbar.inflateMenu(R.menu.menu_accounts) else toolbar.menu.clear()
                epicAccountRecyclerAdapter.setItems(it)
            }
        })
        loginViewModel.userStats.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.error == null) listener!!.onUserSignedIn(it)
            }
        })
        loginViewModel.error.observeSingleEvent(viewLifecycleOwner, Observer {
            if (it != null) Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
            dismissLoading()
        })
        loginViewModel.showLoading.observeSingleEvent(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it == true) showLoading()
            }
        })
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_clear -> {
                loginViewModel.clearSearchHistory()
            }
        }
        return true
    }

    /**
     * View Helpers
     */
    private fun showLoading() {
        linearLayoutHide.visibility = View.GONE
        pbLoading.visibility = View.VISIBLE
        recyclerAccount.visibility = View.GONE
    }

    private fun dismissLoading() {
        linearLayoutHide.visibility = View.VISIBLE
        pbLoading.visibility = View.GONE
        recyclerAccount.visibility = View.VISIBLE
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
            Constants.PLATFORM_XBOX -> getString(R.string.xbl)
            Constants.PLATFORM_PS4 -> getString(R.string.psn)
            Constants.PLATFORM_PC -> getString(R.string.pc)
            else -> null
        }
    }

    /**
     * Interface imp
     */
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
