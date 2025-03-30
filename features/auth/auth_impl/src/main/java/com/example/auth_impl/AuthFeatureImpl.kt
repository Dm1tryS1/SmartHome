package com.example.auth_impl

import com.example.auth_api.AuthFeature
import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.auth_impl.presentation.AuthFragment

class AuthFeatureImpl: AuthFeature {
    override fun createScreen(params: NoParams) = AuthFragment::class.java.createScreen(params)
}