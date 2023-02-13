package com.example.smarthome.fragments.information

import androidx.lifecycle.viewModelScope
import com.example.smarthome.R
import com.example.smarthome.base.presentation.BaseViewModel
import com.example.smarthome.fragments.information.recyclerView.mapper.packageToInfoViewItem
import com.example.smarthome.common.device.Command
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.repository.DeviceRepository
import com.example.smarthome.utils.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InformationViewModel(
    private val informationInteractor: InformationInteractor,
    private val router: Router
) :
    BaseViewModel<InformationState, InformationEvent>() {

    init {
        updateState { state ->
            state.copy(progressVisibility = true)
        }
        sendPackage(Command.BroadCast)
    }

    override fun createInitialState(): InformationState {
        return InformationState(listOf(), true)
    }

    fun sendPackage(aPackage: Command) {
        if (aPackage is Command.BroadCast || !(aPackage is Command.MasterCommand || aPackage is Command.MasterSendDate)) {
            updateState { state ->
                state.copy(progressVisibility = true)
            }
        }
        informationInteractor.sendPackage(aPackage)
    }

    fun getInfo() {
        viewModelScope.launch {
            informationInteractor.getInfo().collectLatest { aPackage ->
                if (aPackage.id == 0
                    && aPackage.type == DeviceRepository.EndOfTransmission.toInt()
                    && aPackage.info0 == DeviceRepository.EndOfTransmission
                    && aPackage.info1 == DeviceRepository.EndOfTransmission
                    && aPackage.info2 == DeviceRepository.EndOfTransmission
                    && aPackage.info3 == DeviceRepository.EndOfTransmission
                ) {
                    updateState { state ->
                        state.copy(progressVisibility = false)
                    }
                } else {
                    if (aPackage.type == SensorType.TemperatureSensor.type || aPackage.type == SensorType.HumidifierSensor.type) {
                        if (aPackage.info1 == DeviceRepository.Less || aPackage.info1 == DeviceRepository.More)
                            if (aPackage.id != null && aPackage.type != null && aPackage.info2 != null)
                                sendEvent(
                                    InformationEvent.ShowNotification(
                                        id = aPackage.id!!,
                                        type = aPackage.type!!,
                                        more = aPackage.info1 == DeviceRepository.More,
                                        comfortableValue = aPackage.info2!!.toInt()
                                    )
                                )
                    }

                    currentViewState.let { informationState ->
                        if (informationState.data != null) {
                            informationState.data.let { currentState ->
                                val sensor = currentState.find { item ->
                                    item.id == aPackage.id
                                }

                                val newState = if (sensor == null) {
                                    currentState + packageToInfoViewItem(aPackage)
                                } else
                                    currentState.map { item ->
                                        if (item.id == aPackage.id)
                                            packageToInfoViewItem(aPackage)
                                        else
                                            item
                                    }

                                updateState {
                                    InformationState(newState.sortedBy {
                                        it.sensorType.type
                                    }, true)
                                }
                            }
                        } else {
                            updateState {
                                InformationState(
                                    listOf(packageToInfoViewItem(aPackage)),
                                    true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onMenuClicked(type: Int, id: Int, info: String, date: String) {
        when (type) {
            SensorType.TemperatureSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_temperature,
                    Command.SensorCommand(id),
                    info,
                    date
                )
            )
            SensorType.PressureSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_pressure,
                    Command.SensorCommand(id),
                    info,
                    date
                )
            )
            SensorType.HumidifierSensor.type -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    R.drawable.ic_humidity,
                    Command.SensorCommand(id),
                    info,
                    date
                )
            )
            SensorType.Conditioner.type -> sendEvent(InformationEvent.OpenConditionerMenuEvent(id))
            SensorType.Humidifier.type -> sendEvent(InformationEvent.OpenHumidifierMenuEvent(id))
            else -> {}
        }
    }

    fun onChartOpen(type: Int, id: Int) {
        if (type == SensorType.TemperatureSensor.type || type == SensorType.HumidifierSensor.type || type == SensorType.PressureSensor.type)
            router.navigateTo(Screens.ChartScreen())
    }

    fun onSettingsClicked() {
        viewModelScope.launch {
            sendEvent(InformationEvent.OpenSettingsMenuEvent(informationInteractor.getUserSettings()))
        }
    }

    fun saveUserSettings(value: Int) {
        informationInteractor.saveUserSettings(value)
    }

    fun onMoreSettings() {
        router.navigateTo(Screens.SystemScreen())
    }
}