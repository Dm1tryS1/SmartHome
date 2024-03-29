package com.example.information_impl.presentation.recyclerView.adapter

import androidx.core.content.ContextCompat
import com.example.data.device.SensorType
import com.example.information_impl.presentation.recyclerView.model.InfoViewItem
import com.example.core.utils.AdapterUtil
import com.example.core.utils.adapterDelegateViewBinding
import com.example.core.utils.bindWithBinding
import com.example.information_impl.databinding.ItemDeviceSeparatorBinding
import com.example.information_impl.databinding.ItemInfoBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class InformationAdapter(onMenuClicked: (InfoViewItem.SensorsInfoViewItem) -> Unit, onDeviceClicked: (SensorType, Int) -> Unit) :
    AsyncListDifferDelegationAdapter<InfoViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createParticipantsAdapter(onMenuClicked, onDeviceClicked),
            createHeaderAdapter()
        )
    )

fun createParticipantsAdapter(onMenuClicked: (InfoViewItem.SensorsInfoViewItem) -> Unit, onDeviceClicked: (SensorType, Int) -> Unit) =
   adapterDelegateViewBinding<InfoViewItem.SensorsInfoViewItem, ItemInfoBinding>(
        ItemInfoBinding::inflate
    ) {
        binding.dropdownMenu.setOnClickListener {
            onMenuClicked(item)
        }

        binding.root.setOnClickListener {
            onDeviceClicked(item.sensorType, item.id)
        }

        bindWithBinding {
            icon.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    item.iconId
                )
            )
            name.text = item.info
        }
    }

fun createHeaderAdapter() =
    adapterDelegateViewBinding<InfoViewItem.Header, ItemDeviceSeparatorBinding>(
        ItemDeviceSeparatorBinding::inflate
    ) {
        bindWithBinding {
            type.text = item.type
        }
    }