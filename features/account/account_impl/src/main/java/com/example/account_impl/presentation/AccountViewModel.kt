package com.example.account_impl.presentation

import ConnectionFeature
import com.example.account_impl.domain.AccountUseCase
import com.example.account_impl.presentation.recyclerView.model.GroupViewItem
import com.example.auth_api.AuthFeature
import com.example.core.navigation.NoParams
import com.example.core.presentation.BaseViewModel
import com.github.terrakok.cicerone.Router

class AccountViewModel(
    private val accountUseCase: AccountUseCase,
    private val features: Features,
    router: Router,
) : BaseViewModel<AccountState, AccountEvent>(router = router) {

    class Features(
        val authFeature: AuthFeature,
        val connectionFeature: ConnectionFeature,
    )

    fun auth() {
        router.navigateTo(features.authFeature.createScreen(NoParams))
    }

    fun addGroup() {
        updateState { state ->
            state.copy(
                groups = state.groups.map {
                    it.copy(status = false)
                } + GroupViewItem.Group(
                    id = state.groups.size,
                    name = "Кабинет ${state.groups.size + 1}",
                    status = true
                )
            )
        }
    }

    fun connectToGroup(groupId: Int) {
        updateState { state ->
            state.copy(
                groups = state.groups.map {
                    if (it.id != groupId) {
                        it.copy(status = false)
                    } else {
                        it.copy(status = true)
                    }
                }
            )
        }
    }

    fun connectSystem() {
        router.navigateTo(features.connectionFeature.createFeature(NoParams))
    }

    fun onGroupClicked(group: GroupViewItem.Group) {
        sendEvent(
            AccountEvent.OpenGroupMenu(
                group = group,
                onConnect = ::connectToGroup
            )
        )
    }

    fun checkAuth() {
        updateState { it.copy(isAuth = accountUseCase()) }
    }

    override fun createInitialState(): AccountState {
        return AccountState(isAuth = false, groups = emptyList())
    }
}