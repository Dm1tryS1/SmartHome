package com.example.account_impl.presentation

import com.example.account_impl.presentation.recyclerView.model.GroupViewItem

sealed interface AccountEvent {
    data class OpenGroupMenu(
        val group: GroupViewItem.Group,
        val onConnect: (Int) -> Unit,
    ) : AccountEvent
}