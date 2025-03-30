package com.example.account_impl.presentation.recyclerView.mapper

import com.example.account_impl.presentation.recyclerView.model.GroupViewItem
import com.example.data.group.GroupDto

fun groupToGroupViewItem(group: GroupDto) = GroupViewItem.Group(
    id = group.id,
    name = group.name,
    status = group.status
)
