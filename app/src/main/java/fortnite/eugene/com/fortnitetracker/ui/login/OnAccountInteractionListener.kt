package fortnite.eugene.com.fortnitetracker.ui.login

import fortnite.eugene.com.fortnitetracker.data.entity.UserAccount

interface OnAccountInteractionListener {
    fun accountClickLogin(userAccount: UserAccount)
    fun accountSwipeDelete(userAccount: UserAccount)
}