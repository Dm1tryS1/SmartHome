package com.example.smarthome.fragments.information

import com.example.network.request
import com.example.shared_preferences.SharedPreferencesService
import com.example.smarthome.repository.DeviceInfoDataBaseRepository
import com.example.smarthome.repository.InformationRepository
import com.example.smarthome.repository.SharedPreferencesRepository
import com.example.storage.entity.DeviceInfo

class InformationUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val deviceInfoDataBaseRepository: DeviceInfoDataBaseRepository,
    private val informationRepository: InformationRepository,
) {
    suspend fun getInfo() = request { informationRepository.getInfo() }

    suspend fun getTemperature(id: Int) =
        request { informationRepository.getTemperature(id) }

    suspend fun getPressure(id: Int) =
        request { informationRepository.getPressure(id) }

    suspend fun getHumidity(id: Int) =
        request { informationRepository.getHumidity(id) }

    suspend fun condCommand(command: String) =
        request { informationRepository.condCommand(command) }

    suspend fun humCommand(command: String) =
        request { informationRepository.humCommand(command) }

    suspend fun setTimer(value: Int) =
        request { informationRepository.setTimer(value) }

    fun saveInDataBase(deviceInfo: com.example.storage.entity.DeviceInfo) {
        deviceInfoDataBaseRepository.saveDeviceInfo(deviceInfo)
    }

    fun getUserSettings() =
        sharedPreferencesRepository.getInt(SharedPreferencesService.userTimer)

    fun saveUserSettings(value: Int) {
        sharedPreferencesRepository.saveInt(SharedPreferencesService.userTimer, value)
    }

}