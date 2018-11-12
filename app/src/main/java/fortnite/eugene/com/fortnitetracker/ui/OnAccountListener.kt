package fortnite.eugene.com.fortnitetracker.ui

import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats

interface OnAccountListener {
    fun onUserSignedIn(accountStats: AccountStats)
    fun onSearchClicked()
}