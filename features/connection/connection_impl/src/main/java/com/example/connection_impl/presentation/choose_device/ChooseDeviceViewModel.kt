package com.example.connection_impl.presentation.choose_device

import androidx.lifecycle.viewModelScope
import com.example.connection_impl.R
import com.example.data.device.ControlType
import com.example.data.device.SensorType
import com.example.connection_impl.presentation.choose_device.recyclerView.model.WifiDevicesItem
import com.example.connection_impl.data.FileRepository
import com.example.connection_impl.domain.ChooseDeviceUseCase
import com.example.connection_impl.presentation.data.ChooseDeviceParams
import com.example.data.wifi.WifiInfo
import com.example.core.presentation.BaseViewModel
import com.example.core.isIpAddress
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseDeviceViewModel(
    private val chooseDeviceUseCase: ChooseDeviceUseCase,
    private val chooseDeviceParams: ChooseDeviceParams,
    router: Router
) :
    BaseViewModel<ChooseDeviceState, ChooseDeviceEvent>(router = router) {

    private fun getDevices(type: Int) = (Gson().fromJson(
        chooseDeviceUseCase.getJSONfromFile(FileRepository.FileName),
        object : TypeToken<Map<String, WifiDevicesItem>>() {}.type
    ) as Map<String, WifiDevicesItem>).filter { it.value.deviceType == type }
        .map {
            it.value
        }

    override fun createInitialState(): ChooseDeviceState {
        return ChooseDeviceState.OnSuccess(devices = getDevices(SensorType.Conditioner.type))
    }

    fun changeList(type: Type) {
        val devices = when (type) {
            Type.TypeCond -> getDevices(SensorType.Conditioner.type)
            Type.TypeHum -> getDevices(SensorType.Humidifier.type)
        }
        updateState {
            ChooseDeviceState.OnSuccess(devices)
        }
    }

    fun onItemClicked(id: Int) {
        when (chooseDeviceParams.controlType) {
            ControlType.IP -> sendEvent(ChooseDeviceEvent.OpenDeviceMenuByIP(id))
            ControlType.Connect -> {
                val wifiInfo = chooseDeviceUseCase.getWifiInfo()
                if (wifiInfo != null) {
                    sendEvent(
                        ChooseDeviceEvent.OpenDeviceMenu(
                            id,
                            wifiInfo
                        )
                    )
                } else {
                    sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_error))
                }
            }
        }
    }

    fun connectByIp(id: Int, ip: String) {
        if (ip.isIpAddress()) {
            updateState {
                ChooseDeviceState.Loading(true)
            }
            finishConnection(id, ip)
        } else {
            sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_error_ip_format))
        }
    }

    fun connect(id: Int, wifiInfo: WifiInfo) {
        if (!(wifiInfo.ssid.isEmpty() || wifiInfo.password.isEmpty())) {
            viewModelScope.launch(Dispatchers.IO) {
                updateState {
                    ChooseDeviceState.Loading(true)
                }
                chooseDeviceUseCase.connect(wifiInfo) { ip ->
                    if (!ip.isNullOrEmpty()) {
                        finishConnection(id, ip)
                    } else {
                        sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_connection_error))
                        updateState {
                            ChooseDeviceState.Loading(false)
                        }
                    }
                }
            }
        } else {
            sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_error_data))
        }
    }

    private fun finishConnection(id: Int, ip: String) {
        viewModelScope.launch {
            if (chooseDeviceUseCase.sendConfig(listOf(Pair(ip, id))).data != null) {
                sendEvent(ChooseDeviceEvent.OnSuccess)
            } else {
                sendEvent(ChooseDeviceEvent.OnError(R.string.connect_device_error_send_config))
            }

            updateState {
                ChooseDeviceState.Loading(false)
            }
        }
    }

    enum class Type {
        TypeCond, TypeHum
    }
}