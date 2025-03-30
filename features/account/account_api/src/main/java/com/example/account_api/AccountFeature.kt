package com.example.account_api

import com.example.core.navigation.NoParams
import com.github.terrakok.cicerone.Screen

interface AccountFeature {
    fun createScreen(params: NoParams) : Screen
}
