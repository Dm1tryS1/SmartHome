package com.example.account_impl.presentation.recyclerView.model

sealed interface GroupViewItem {
    data class Group(
        val id: Int,
        val name: String,
        var status: Boolean
    ) : GroupViewItem
}

