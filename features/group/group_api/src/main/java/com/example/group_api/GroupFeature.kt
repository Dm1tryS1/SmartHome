package com.example.group_api

import com.example.core.navigation.NoParams
import com.github.terrakok.cicerone.Screen

interface GroupFeature {
    fun createScreen(params: NoParams) : Screen
}
