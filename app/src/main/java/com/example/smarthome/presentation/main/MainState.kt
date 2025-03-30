package com.example.smarthome.presentation.main

import android.view.MenuItem
import com.example.smarthome.R

data class MainState(
    val currentTab: TabType,
)

enum class TabType {
    Home,
    Account,
}

object TabsMapper {
    fun mapItemIdToTab(item: MenuItem) = when (item.itemId) {
        R.id.home -> TabType.Home
        R.id.account -> TabType.Account
        else -> null
    }

    fun mapTabToItemId(tab: TabType): Int = when (tab) {
        TabType.Home -> R.id.home
        TabType.Account -> R.id.account
    }
}