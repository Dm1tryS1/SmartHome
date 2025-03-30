package com.example.auth_api

import com.example.core.navigation.NoParams
import com.github.terrakok.cicerone.Screen

interface AuthFeature {
    fun createScreen(params: NoParams) : Screen
}
