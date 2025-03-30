package com.example.account_impl

import com.example.account_api.AccountFeature
import com.example.auth_api.AuthFeature
import com.example.core.navigation.NoParams
import com.example.core.navigation.createScreen
import com.example.account_impl.presentation.AccountFragment

class AccountFeatureImpl: AccountFeature {
    override fun createScreen(params: NoParams) = AccountFragment::class.java.createScreen(params)
}