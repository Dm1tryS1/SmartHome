package com.example.smarthome.fragments.charts

import com.example.smarthome.repository.DeviceInfoDataBaseRepository

class ChartUseCase(private val dataBaseRepository: DeviceInfoDataBaseRepository) {
    fun getDeviceInfo(id: Int, date: String) = dataBaseRepository.getDeviceInfoByDate(id, date)
}