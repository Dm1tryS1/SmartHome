package com.example.chart_impl.presentation

import com.example.data.device.SensorType
import com.example.core.R
import com.github.mikephil.charting.data.Entry

data class ChartsState(
    val data: List<Entry>?,
    val listDates: ArrayList<String>,
    val deviceTypes: SensorType?,
    val graphColor: Int = R.color.blue,
    val pointColor: Int =  R.color.gray1
)
