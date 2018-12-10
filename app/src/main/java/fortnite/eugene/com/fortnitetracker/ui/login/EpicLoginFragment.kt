package fortnite.eugene.com.fortnitetracker.ui.login

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseFragment
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import fortnite.eugene.com.fortnitetracker.ui.MainActivity
import fortnite.eugene.com.fortnitetracker.utils.Constants
import fortnite.eugene.com.fortnitetracker.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_login.*

class EpicLoginFragment : BaseFragment(), OnAccountInteractionListener, View.OnClickListener,
    TextView.OnEditorActionListener {

    companion object {
        val TAG: String = EpicLoginFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = EpicLoginFragment()
    }

    override val layoutId: Int = R.layout.fragment_login
    override val scrollFlags: Int? = Constants.SCROLL_FLAG_DEFAULT
    private var mainActivity: MainActivity? = null

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var epicAccountRecyclerAdapter: EpicAccountRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        epicAccountRecyclerAdapter = EpicAccountRecyclerAdapter(context!!, this)
        recyclerAccount.adapter = epicAccountRecyclerAdapter
        ItemTouchHelper(object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                accountSwipeDelete(epicAccountRecyclerAdapter.getItem(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerAccount)
        toggleButtonPlatform.onToggledListener = { toggle, _ ->
            textInputUser.hint = resources.getStringArray(R.array.platform_toggle)[toggle.position]
        }
        editTextEpicUserName.setOnEditorActionListener(this)
        btnSearch.setOnClickListener(this)
        imgDeleteSearchHistory.setOnClickListener(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initToolbar(getString(R.string.search_player_stats), null, R.drawable.ic_search_24dp)
        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        observeAccounts()
        observeUserData()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchAccount()
            return true
        }
        return false
    }

    override fun onClick(v: View?) {
        if (v == btnSearch) {
            searchAccount()
        } else if (v == imgDeleteSearchHistory) {
            snackbar("Clear Search History?").setAction("Clear") {
                loginViewModel.clearSearchHistory()
            }.show()
        }
    }

    private fun searchAccount() {
        getBaseActivity().hideKeyboard()
        if (getEpicName().isNullOrBlank()) {
            Toast.makeText(context, getString(R.string.enter_epic_username), Toast.LENGTH_SHORT).show()
            return
        }
        loginViewModel.getStats(getPlatform()!!, getEpicName()!!)
    }

    private fun observeAccounts() {
        loginViewModel.userAccountList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                historyHeader.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
                epicAccountRecyclerAdapter.setItems(it)
            }
        })
    }

    private fun observeUserData() {
        loginViewModel.userStats.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.error == null) mainActivity!!.onLogin(it)
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

    private fun getEpicName(): String? {
        return editTextEpicUserName.text!!.trim().toString()
    }

    private fun getPlatform(): String? {
        return when (toggleButtonPlatform.getSelectedTogglePosition()) {
            Constants.PLATFORM_XBOX_INT -> Constants.PLATFORM_XBOX_STRING
            Constants.PLATFORM_PS4_INT -> Constants.PLATFORM_PS4_STRING
            Constants.PLATFORM_PC_INT -> Constants.PLATFORM_PC_STRING
            else -> null
        }
    }

    override fun accountClickLogin(userAccount: UserAccount) {
        loginViewModel.getStats(userAccount.platformName, userAccount.displayName)
//        loginViewModel.getUserStats(userAccount.platformName, userAccount.displayName)
    }

    override fun accountSwipeDelete(userAccount: UserAccount) {
        snackbar(userAccount.epicUserHandle + " deleted").setAction("Undo") {
            loginViewModel.undoDeletedAccount()
        }.show()
        loginViewModel.deleteAccount(userAccount)
    }

    /**
     * View Helpers
     */
    private fun showLoading() {
        pbLoading.visibility = View.VISIBLE
        cardLoginHolder.visibility = View.GONE
        recyclerAccount.visibility = View.GONE
    }

    private fun dismissLoading() {
        pbLoading.visibility = View.GONE
        cardLoginHolder.visibility = View.VISIBLE
        recyclerAccount.visibility = View.VISIBLE
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            this.mainActivity = context
        }
    }

    override fun onDetach() {
        mainActivity = null
        super.onDetach()
    }
}
