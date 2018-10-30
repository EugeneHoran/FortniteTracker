package fortnite.eugene.com.fortnitetracker.ui.shared

import fortnite.eugene.com.fortnitetracker.model.stats.AccountStats

interface OnAccountListener {
    fun onUserSignedIn(accountStats: AccountStats)
}