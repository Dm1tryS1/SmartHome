package com.example.settings_impl.presentation

import ConnectionFeature
import androidx.lifecycle.viewModelScope
import com.example.core.navigation.NoParams
import com.example.core.presentation.BaseViewModel
import com.example.data.wifi.WifiInfo
import com.example.settings_impl.R
import com.example.settings_impl.presentation.domain.SettingsUseCase
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsUseCase: SettingsUseCase,
    router: Router,
    private val features: Features
) : BaseViewModel<SettingsState, SettingsEvent>(router = router) {

    class Features(
        val connection: ConnectionFeature
    )

    fun onWifiClicked() {
        router.navigateTo(features.connection.createFeature(NoParams))
    }

    fun connect(wifiInfo: WifiInfo) {
        updateState { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            features.connection.connect(wifiInfo) { ip ->
                if (!ip.isNullOrEmpty()) {
                    settingsUseCase.saveSystemIp(ip)
                    sendEvent(SettingsEvent.ShowSnack(R.string.settings_connected))
                } else {
                    sendEvent(SettingsEvent.ShowSnack(R.string.settings_fail))
                }
                updateState { state ->
                    state.copy(isLoading = false)
                }
            }
        }
    }

    fun onConnectClicked() {
        val wifiInfo = features.connection.getWifiInfo()
        if (wifiInfo != null) {
            sendEvent(SettingsEvent.OpenDialog(wifiInfo))
        } else {
            sendEvent(SettingsEvent.ShowSnack(R.string.connect_device_error))
        }
    }

    override fun onBackPressed(): Boolean {
        router.backTo(null)
        return !super.onBackPressed()
    }

    override fun createInitialState(): SettingsState {
        return SettingsState(false)
    }
}