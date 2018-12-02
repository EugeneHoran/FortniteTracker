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
    override val scrollFlags: Int? = Constants.SCROLL_FLAG_NONE

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

    override fun onAccountClicked(userAccount: UserAccount) {
        loginViewModel.getUserStats(userAccount.platformName, userAccount.epicUserHandle)
    }

    override fun onAccountDeleted(userAccount: UserAccount) {
        snackbar(userAccount.epicUserHandle + " deleted").setAction("Undo") {
            loginViewModel.undoDeletedAccount()
        }.show()
        loginViewModel.deleteAccount(userAccount)
    }

    private fun observeUserData() {
        loginViewModel.userAccountList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    getBaseActivity().onInflateMenu(R.menu.menu_accounts)
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
            Constants.PLATFORM_XBOX -> getString(R.string.xbl)
            Constants.PLATFORM_PS4 -> getString(R.string.psn)
            Constants.PLATFORM_PC -> getString(R.string.pc)
            else -> null
        }
    }

    /**
     * View Helpers
     */
    private fun showLoading() {
        toggleButtonPlatform.visibility = View.GONE
        textInputUser.visibility = View.GONE
        btnSearch.visibility = View.GONE

        pbLoading.visibility = View.VISIBLE
        recyclerAccount.visibility = View.GONE
    }

    private fun dismissLoading() {
        toggleButtonPlatform.visibility = View.VISIBLE
        textInputUser.visibility = View.VISIBLE
        btnSearch.visibility = View.VISIBLE

        pbLoading.visibility = View.GONE
        recyclerAccount.visibility = View.VISIBLE
    }
}
