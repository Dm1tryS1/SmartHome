package com.example.account_impl.presentation

import com.example.account_impl.presentation.recyclerView.model.GroupViewItem

data class AccountState(
    val isAuth: Boolean,
    val groups: List<GroupViewItem.Group>,
)

