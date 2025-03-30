package com.example.account_impl.presentation

import com.example.account_impl.presentation.domain.AccountUseCase
import com.example.auth_api.AuthFeature
import com.example.core.navigation.NoParams
import com.example.core.presentation.BaseViewModel
import com.example.settings_api.SettingsFeature
import com.github.terrakok.cicerone.Router

class AccountViewModel(
    private val accountUseCase: AccountUseCase,
    private val features: Features,
    router: Router,
) : BaseViewModel<AccountState, AccountEvent>(router = router) {

    class Features(
        val authFeature: AuthFeature,
        val settingsFeature: SettingsFeature,
    )

    fun auth() {
        router.navigateTo(features.authFeature.createScreen(NoParams))
    }

    fun addGroup() {
        updateState { it.copy(hasGroups = true) }
    }

    fun connectSystem() {
        router.navigateTo(features.settingsFeature.createScreen(NoParams))
    }

    fun checkAuth() {
        updateState { it.copy(isAuth = accountUseCase()) }
    }

    override fun createInitialState(): AccountState {
        return AccountState(isAuth = false, hasGroups = false)
    }
}