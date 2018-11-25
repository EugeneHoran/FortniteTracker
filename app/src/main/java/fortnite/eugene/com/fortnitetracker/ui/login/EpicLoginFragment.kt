package fortnite.eugene.com.fortnitetracker.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import fortnite.eugene.com.fortnitetracker.R
import fortnite.eugene.com.fortnitetracker.base.BaseFragment
import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount
import fortnite.eugene.com.fortnitetracker.utils.Constants
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
    }

    override fun initData(savedInstanceState: Bundle?, viewModel: LoginViewModel) {
        initToolbar(getString(R.string.search_player_stats))
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
}
