package com.example.group_impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.navigation.NoParams
import com.example.core.presentation.BaseViewModel
import com.example.settings_api.SettingsFeature
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupViewModel(
    router: Router,
    private val features: Features,
) : BaseViewModel<GroupState, GroupEvent>(router = router) {

    class Features(
        val settingsFeature: SettingsFeature,
    )

    fun createGroup() {
        updateState { state ->
            state.copy(isLoading = true, error = null)
        }
        viewModelScope.launch(Dispatchers.IO) {
            router.navigateTo(features.settingsFeature.createScreen(NoParams))
        }
    }

    override fun createInitialState(): GroupState {
        return GroupState(isLoading = false, error = null)
    }
}