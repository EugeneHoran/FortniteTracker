package fortnite.eugene.com.fortnitetracker.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseFragment
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import fortnite.eugene.com.fortnitetracker.utils.Constants
import fortnite.eugene.com.fortnitetracker.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_login.*

class EpicLoginFragment : BaseFragment<LoginViewModel>(),
    EpicAccountRecyclerAdapter.EpicAccountClickListener {

    companion object {
        val TAG: String = EpicLoginFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = EpicLoginFragment()
    }

    override val layoutId: Int = R.layout.fragment_login
    override val scrollFlags: Int? = Constants.SCROLL_FLAG_DEFAULT

    private lateinit var loginViewModel: LoginViewModel
    private var epicAccountRecyclerAdapter = EpicAccountRecyclerAdapter(this)

    override fun getViewModel(): LoginViewModel {
        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        return loginViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSearch.setOnClickListener { searchAccount() }
        recyclerAccount.adapter = epicAccountRecyclerAdapter
        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerAccount.adapter as EpicAccountRecyclerAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerAccount)

        toggleButtonPlatform.onToggledListener = { toggle, _ ->
            textInputUser.hint = resources.getStringArray(R.array.platform_toggle)[toggle.position]
        }
        imgDeleteSearchHistory.setOnClickListener {
            snackbar("Clear Search History?").setAction("Clear") {
                loginViewModel.clearSearchHistory()
            }.show()
        }
    }


    override fun initData(savedInstanceState: Bundle?, viewModel: LoginViewModel) {
        initToolbar(getString(R.string.search_player_stats), null, R.drawable.ic_search_24dp)
        observeUserData()
    }

    private fun searchAccount() {
        getBaseActivity().hideKeyboard()
        if (getEpicName().isNullOrBlank()) {
            Toast.makeText(context, getString(R.string.enter_epic_username), Toast.LENGTH_SHORT).show()
            return
        }
        loginViewModel.getUserStats(getPlatform()!!, getEpicName()!!)
    }

    private fun observeUserData() {
        loginViewModel.userAccountList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    historyHeader.visibility = View.VISIBLE
                } else {
                    historyHeader.visibility = View.GONE
                }
                epicAccountRecyclerAdapter.setItems(it)
            }
        })
        loginViewModel.userStats.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.error == null) getBaseActivity().onLogin(it)
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

    override fun accountItemClicked(userAccount: UserAccount) {
        loginViewModel.getUserStats(userAccount.platformName, userAccount.displayName)
    }

    override fun deletedAccount(userAccount: UserAccount) {
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
}
