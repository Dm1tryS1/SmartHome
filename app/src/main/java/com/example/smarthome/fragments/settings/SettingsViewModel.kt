package com.example.smarthome.fragments.settings

import androidx.lifecycle.viewModelScope
import com.example.smarthome.R
import com.example.smarthome.core.base.presentation.BaseViewModel
import com.example.smarthome.common.wifi.WifiInfo
import com.example.smarthome.main.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val devicesUseCase: DevicesUseCase,
    private val router: Router
) : BaseViewModel<SettingsState, SettingsEvent>() {

    fun findDevices() {
        viewModelScope.launch {
            val devices = devicesUseCase.findDevices()
            updateState { state ->
                state.copy(devices = devices)
            }
        }
    }

    fun onWifiClicked() {
        router.navigateTo(Screens.ConnectDeviceScreen())
    }

    private fun finishConnection(ip: String, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            devicesUseCase.saveSystemIp(ip)

            val data = mutableListOf<Pair<String, Int>>()
            data.add(Pair("192.168.1.1", 2))
            data.add(Pair("192.168.1.2", 4))

//            val cond = devicesUseCase.getCondInfo()
//            val hum = devicesUseCase.getHumIpInfo()
//
//            if (!cond.first.isNullOrEmpty() && cond.second != -1) {
//                data.add(Pair(cond.first!!, cond.second))
//            }
//            if (!hum.first.isNullOrEmpty() && hum.second != -1) {
//                data.add(Pair(hum.first!!, hum.second))
//            }

            if (data.isNotEmpty()) {
                devicesUseCase.sendConfig(ip, data) { result ->
                    if (result) {
                        bluetoothConnection(address)
                    } else {
                        sendEvent(SettingsEvent.DisconnectFailureEvent)
                        updateState { state ->
                            state.copy(isLoading = false)
                        }
                    }
                }
            } else {
                bluetoothConnection(address)
            }
        }
    }

    private fun bluetoothConnection(address: String) {
        viewModelScope.launch {
            devicesUseCase.connect(address) {
                if (it) {
                    sendEvent(SettingsEvent.ConnectionSuccessEvent)
                } else {
                    sendEvent(SettingsEvent.ConnectionFailureEvent)
                }

                updateState { state ->
                    state.copy(isLoading = false)
                }
            }
        }
    }

    fun connect(address: String, wifiInfo: WifiInfo) {
        updateState { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                devicesUseCase.connectWifiModule(wifiInfo) { ip ->
                    if (!ip.isNullOrEmpty()) {
                        finishConnection(ip, address)
                    } else {
                        sendEvent(SettingsEvent.DisconnectFailureEvent)
                        updateState { state ->
                            state.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun onItemClicked(address: String) {
        val wifiInfo = devicesUseCase.getWifiInfo()
        if (wifiInfo != null) {
            sendEvent(SettingsEvent.OnItemClickedEvent(address, wifiInfo))
        } else {
            sendEvent(SettingsEvent.Error(R.string.connect_device_error))
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            if (devicesUseCase.disconnect())
                sendEvent(SettingsEvent.DisconnectSuccessEvent)
            else
                sendEvent(SettingsEvent.DisconnectFailureEvent)
        }
    }

    override fun createInitialState(): SettingsState {
        return SettingsState(listOf(), false)
    }
}