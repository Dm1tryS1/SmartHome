package com.example.smarthome.fragments.information.recyclerView.mapper

import com.example.smarthome.R
import com.example.smarthome.fragments.information.data.Package
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import kotlin.experimental.and

fun packageToInfoViewItem(aPackage: Package): InfoViewItem {
    return when (aPackage.id) {
        1,3 -> packageToTemperatureInfo(aPackage)
        2 -> packageToConditionerInfo(aPackage)
        5,6 -> packageToHumidityInfo(aPackage)
        4 -> packageToHumidifierInfo(aPackage)
        7,8 -> packageToPressure(aPackage)
        else -> {
            InfoViewItem(R.drawable.ic_info, aPackage.id ?: 0, "Ошибка", "Ошибка")
        }
    }
}

fun packageToTemperatureInfo(aPackage: Package): InfoViewItem {
    val date = with(aPackage) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours!!.toTime()}:${
                minutes!!.toTime()
            }:${seconds!!.toTime()}"
    }

    val info = aPackage.info.let {
        if (it == null)
            "Нет информации"
        else
            "Температура: $it°C"
    }

    return InfoViewItem(R.drawable.ic_temperature, aPackage.id!!, info, date)
}

fun packageToHumidityInfo(aPackage: Package): InfoViewItem {
    val date = with(aPackage) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours!!.toTime()}:${
                minutes!!.toTime()
            }:${seconds!!.toTime()}"
    }

    val info = aPackage.info.let {
        if (it == null)
            "Нет информации"
        else
            "Влажность: $it%"
    }

    return InfoViewItem(R.drawable.ic_humidity, aPackage.id!!, info, date)
}

fun packageToHumidifierInfo(aPackage: Package): InfoViewItem {
    val date = with(aPackage) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours!!.toTime()}:${
                minutes!!.toTime()
            }:${seconds!!.toTime()}"
    }

    val info = aPackage.info.let {
        if (it == null)
            "Нет информации"
        else
            if ((it.toByte() and 128.toByte()) == 0.toByte())
                "Выключено"
            else
                "Включено: ${
                    (it.toByte() and 127.toByte())
                } % воды"
    }

    return InfoViewItem(R.drawable.ic_humidifier, aPackage.id!!, info, date)
}

fun packageToConditionerInfo(aPackage: Package): InfoViewItem {
    val date = with(aPackage) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours!!.toTime()}:${
                minutes!!.toTime()
            }:${seconds!!.toTime()}"
    }

    val info = aPackage.info.let {
        if (it == null)
            "Нет информации"
        else
            if ((it.toByte() and 128.toByte()) == 0.toByte())
                "Выключено"
            else
                "Включено: ${
                    (it.toByte() and 63.toByte()).toString(16)
                } °C"
    }

    return InfoViewItem(R.drawable.ic_conditioner, aPackage.id!!, info, date)
}

fun packageToPressure(aPackage: Package): InfoViewItem {
    val date = with(aPackage) {
        if (hours == null || minutes == null || seconds == null)
            "Нет информации"
        else
            "${hours!!.toTime()}:${
                minutes!!.toTime()
            }:${seconds!!.toTime()}"
    }

    val info = aPackage.info.let {
        if (it == null)
            "Нет информации"
        else
            "Давление: $it кПа"
    }

    return InfoViewItem(R.drawable.ic_pressure, aPackage.id!!, info, date)
}

fun Int.toTime(): String {
    return this.toString().padStart(2,'0')
}
