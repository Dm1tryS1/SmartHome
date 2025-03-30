package com.example.account_impl.presentation.recyclerView.adapter

import androidx.core.view.isGone
import com.example.account_impl.presentation.recyclerView.model.GroupViewItem
import com.example.core.utils.AdapterUtil
import com.example.core.utils.adapterDelegateViewBinding
import com.example.core.utils.bindWithBinding
import com.example.settings_impl.databinding.ItemGroupBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class GroupsAdapter(
    onMenuClicked: (GroupViewItem.Group) -> Unit,
    onItemClicked: (GroupViewItem.Group) -> Unit,
) :
    AsyncListDifferDelegationAdapter<GroupViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createParticipantsAdapter(onMenuClicked, onItemClicked),
        )
    )

fun createParticipantsAdapter(
    onMenuClicked: (GroupViewItem.Group) -> Unit,
    onItemClicked: (GroupViewItem.Group) -> Unit,
) =
    adapterDelegateViewBinding<GroupViewItem.Group, ItemGroupBinding>(
        ItemGroupBinding::inflate
    ) {
        binding.dropdownMenu.setOnClickListener {
            onMenuClicked(item)
        }

        binding.root.setOnClickListener {
            onItemClicked(item)
        }

        bindWithBinding {
            name.text = item.name
            status.isSelected = item.status
            status.text = if (item.status) {
                "Подключено"
            } else {
                "Не подключено"
            }

            dropdownMenu.isGone = !item.status
        }
    }