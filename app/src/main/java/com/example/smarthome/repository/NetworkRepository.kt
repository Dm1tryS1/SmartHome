package com.example.smarthome.repository

import com.example.smarthome.common.getTime
import com.example.smarthome.service.network.NetworkModule
import com.example.smarthome.service.network.mapper.*
import com.example.smarthome.service.network.model.*

class NetworkRepository(private val networkModule: NetworkModule) {

    suspend fun sendConfig(
        wifiDevicesItem: List<SendConfigRequest>,
        systemIp: String,
    ) =
        try {
            networkModule.createConfigService(systemIp).sendConfig(wifiDevicesItem)
            true
        } catch (e: Exception) {
            false
        }


    suspend fun getInfo(
        systemIp: String,
    ) =
        try {
            val response = networkModule.createConfigService(systemIp).getAll()
            getAllResponseMapper(response, getTime())
        } catch (e: Exception) {
            emptyList()
        }

    suspend fun getTemperature(
        id: Int,
        systemIp: String,
    ) =
        try {
            val response = networkModule.createConfigService(systemIp).temperature(id)
            temperatureResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun getPressure(
        id: Int,
        systemIp: String,
    ) =
        try {
            val response = networkModule.createConfigService(systemIp).pressure(id)
            pressureResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun getHumidity(
        id: Int,
        systemIp: String,
    ) =
        try {
            val response = networkModule.createConfigService(systemIp).humidity(id)
            humidityResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun condCommand(
        systemIp: String,
        command: String
    ) =
        try {
            val response =
                networkModule.createConfigService(systemIp).condcommand(ConditionerRequest(command))
            conditionerResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun humCommand(
        systemIp: String,
        command: String
    ) =
        try {
            val response =
                networkModule.createConfigService(systemIp).humcommand(HumidifierRequest(command))
            humidifierResponseMapper(response, getTime())
        } catch (e: Exception) {
            null
        }

    suspend fun setTimer(
        systemIp: String,
        value: Int
    ) =
        try {
            networkModule.createConfigService(systemIp).systemtimer(SystemTimerRequest(value))
        } catch (e: Exception) {
            null
        }

    suspend fun setSystemSettings(
        systemIp: String,
        minTemp: Int,
        maxTemp: Int,
        minHum: Int,
        maxHum: Int,
        displayedValue: Int
    ) =
        try {
            networkModule.createConfigService(systemIp).systemsettings(
                SystemSettingsRequest(
                    minHum = minHum,
                    maxHum = maxHum,
                    minTemp = minTemp,
                    maxTemp = maxTemp,
                    displayedValue = displayedValue
                )
            )
        } catch (e: Exception) {
            null
        }

    suspend fun writeCommandForRemoteControl(systemIp: String, deviceType: Int, command: String) = try {
        networkModule.createConfigService(systemIp).irreceiver(IrReceiverRequest(command, deviceType))
    } catch (e: Exception) {
        null
    }

}