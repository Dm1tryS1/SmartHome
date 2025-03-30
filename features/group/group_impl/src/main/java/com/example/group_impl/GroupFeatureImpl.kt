package com.example.group_impl

import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.group_impl.presentation.GroupFragment
import com.example.group_api.GroupFeature

class GroupFeatureImpl : GroupFeature {
    override fun createScreen(params: NoParams) = GroupFragment::class.java.createScreen(params)
}