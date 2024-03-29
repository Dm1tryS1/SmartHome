package com.example.information_impl.data

import com.example.data.getTime
import com.example.information_impl.data.api.InformationService
import com.example.information_impl.data.mapper.*
import com.example.information_impl.data.model.ConditionerRequest
import com.example.information_impl.data.model.HumidifierRequest
import com.example.information_impl.data.model.SystemTimerRequest
import com.example.network.NetworkFactory

class InformationRepository(
    networkModule: NetworkFactory
) {

    private var informationService = networkModule.createService(InformationService::class.java)

    suspend fun getInfo() =
        getAllResponseMapper(
            informationService.getAll(),
            getTime()
        )

    suspend fun getTemperature(
        id: Int
    ) = temperatureResponseMapper(informationService.temperature(id), getTime())


    suspend fun getPressure(
        id: Int
    ) = pressureResponseMapper(informationService.pressure(id), getTime())


    suspend fun getHumidity(
        id: Int
    ) = humidityResponseMapper(informationService.humidity(id), getTime())


    suspend fun condCommand(
        command: String
    ) = conditionerResponseMapper(
        informationService.condcommand(ConditionerRequest(command)), getTime()
    )

    suspend fun humCommand(
        command: String
    ) = humidifierResponseMapper(
        informationService.humcommand(HumidifierRequest(command)),
        getTime()
    )

    suspend fun setTimer(
        value: Int
    ) =
        informationService.systemtimer(SystemTimerRequest(value))
}