package com.example.smarthome.fragments.information

import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem

data class InformationState(val data: List<InfoViewItem.SensorsInfoViewItem>?, val progressVisibility: Boolean = true)