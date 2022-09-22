package com.example.ledcontroller.fragments.information.recyclerView.mapper


import com.example.ledcontroller.R
import com.example.ledcontroller.fragments.information.data.Package
import com.example.ledcontroller.fragments.information.recyclerView.model.InfoViewItem
import kotlin.experimental.and

fun packageToInfoViewItem(aPackage: Package): InfoViewItem {
    return when(aPackage.id){
        1 -> packageToTemperatureInfo(aPackage)
        2 -> packageToConditionerInfo(aPackage)
        else -> { InfoViewItem(R.drawable.ic_info, 0, "Ошибка", "Ошибка") }
    }
}

fun packageToTemperatureInfo(aPackage: Package): InfoViewItem {
    val date = aPackage.date.let {
        if (it == null || it == 0)
            "Нет информации"
        else
            it.toString()
    }
    val info = aPackage.info.let {
        if (it == null)
            "Нет информации"
        else
            "Температура: $it °C"
    }

    return InfoViewItem(R.drawable.ic_temperature, aPackage.id!!, info, date)
}

fun packageToConditionerInfo(aPackage: Package): InfoViewItem{
    val date = aPackage.date.let {
        if (it == null || it == 0)
            "Нет информации"
        else
            it.toString()
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