package com.example.information_impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.charts_api.ChartsFeature
import com.example.core.navigation.NoParams
import com.example.data.device.SensorType
import com.example.core.presentation.BaseViewModel
import com.example.data.DeviceInfoSchema
import com.example.information_impl.domain.InformationUseCase
import com.example.information_impl.presentation.recyclerView.mapper.packageToInfoViewItem
import com.example.information_impl.presentation.recyclerView.model.InfoViewItem
import com.example.system_api.SystemFeature
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class InformationViewModel(
    private val informationUseCase: InformationUseCase,
    router: Router,
    private val features: Features
) : BaseViewModel<InformationState, InformationEvent>(router = router) {

    class Features(
        val systemFeature: SystemFeature,
        val chartsFeature: ChartsFeature
    )

    override fun createInitialState(): InformationState {
        return InformationState(listOf(), true)
    }

    private fun makeNotification(schema: DeviceInfoSchema.Sensors.HumidityAndTemperatureSensorSchema) {
        if (schema.notification)
            sendEvent(
                InformationEvent.ShowNotification(
                    id = schema.id,
                    type = schema.type,
                    more = schema.data < schema.min,
                    comfortableValue = if (schema.data < schema.min) schema.min else schema.max
                )
            )
    }

    private fun saveInDataBase(deviceInfoSchema: DeviceInfoSchema.Sensors) {
        informationUseCase.saveInDataBase(
            com.example.storage.entity.DeviceInfo(
                deviceId = deviceInfoSchema.id,
                time = "${deviceInfoSchema.hours}:${deviceInfoSchema.minutes}",
                value = deviceInfoSchema.data,
                date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            )
        )
    }


    fun getInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val newState = mutableListOf<InfoViewItem.SensorsInfoViewItem>()
            informationUseCase.getInfo().data?.forEach { schema ->
                if (schema is DeviceInfoSchema.Sensors.HumidityAndTemperatureSensorSchema) {
                    makeNotification(schema)
                }

                if (schema is DeviceInfoSchema.Sensors) {
                    saveInDataBase(schema)
                }
                newState.add(packageToInfoViewItem(schema))
            }
            updateState { InformationState(newState, false) }
        }

    }

    private fun update(response: DeviceInfoSchema) {
        if (response is DeviceInfoSchema.Sensors) { saveInDataBase(response) }
        val newState = currentViewState.data?.map { item ->
            if (item.id == response.id) {
                packageToInfoViewItem(response)
            } else {
                item
            }
        }
        updateState { InformationState(newState, false) }
    }

    private fun getTemperature(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationUseCase.getTemperature(id).data
            if (response != null) update(response)

        }
    }

    private fun getSenorInfo(id: Int, type: SensorType) {
        when (type) {
            SensorType.TemperatureSensor -> getTemperature(id)
            SensorType.HumiditySensor -> getHumidity(id)
            SensorType.PressureSensor -> getPressure(id)
            else -> { }
        }
    }

    private fun getPressure(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationUseCase.getPressure(id).data
            if (response != null) update(response)
        }
    }

    private fun getHumidity(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationUseCase.getHumidity(id).data
            if (response != null) update(response)
        }
    }

    private fun sendCondCommand(command: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationUseCase.condCommand(command).data
            if (response != null) update(response)
        }
    }

    private fun sendHumCommand(command: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = informationUseCase.humCommand(command).data
            if (response != null) update(response)
        }
    }

    fun onMenuClicked(deviceInfo: InfoViewItem.SensorsInfoViewItem) {
        when (deviceInfo.sensorType) {
            SensorType.TemperatureSensor, SensorType.PressureSensor, SensorType.HumiditySensor -> sendEvent(
                InformationEvent.OpenSensorMenuEvent(
                    deviceInfo,
                    this::getSenorInfo,
                )
            )
            SensorType.Conditioner -> sendEvent(
                InformationEvent.OpenConditionerMenuEvent(
                    deviceInfo.id,
                    deviceInfo.status,
                    this::sendCondCommand
                )
            )
            SensorType.Humidifier -> sendEvent(
                InformationEvent.OpenHumidifierMenuEvent(
                    deviceInfo.id,
                    deviceInfo.status,
                    this::sendHumCommand
                )
            )
            else -> { }
        }
    }

    fun onChartOpen(type: SensorType, id: Int) {
        if (type == SensorType.TemperatureSensor || type == SensorType.HumiditySensor || type == SensorType.PressureSensor)
            router.navigateTo(features.chartsFeature.createScreen(ChartsFeature.ChartsParams(type, id)))
    }

    fun onSettingsClicked() {
        router.navigateTo(features.systemFeature.createScreen(NoParams))
    }
}