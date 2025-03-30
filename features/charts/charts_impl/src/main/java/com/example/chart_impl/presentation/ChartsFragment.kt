package com.example.chart_impl.presentation

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getColor
import com.example.data.device.SensorType
import com.example.core.presentation.BaseFragment
import com.example.core.dp
import com.example.core.fragmentViewBinding
import com.example.core.setupEnvironments
import com.example.core.toTime
import com.example.chart_impl.presentation.formatter.SensorDateFormatter
import com.example.chart_impl.presentation.formatter.SensorValueFormatter
import com.example.charts_api.ChartsFeature
import com.example.charts_impl.R
import com.example.charts_impl.databinding.FragmentChartsBinding
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class ChartsFragment : BaseFragment<ChartsState, ChartsEvent>(R.layout.fragment_charts) {

    private var xAxisFormatter = SensorDateFormatter()

    private val binding by fragmentViewBinding(FragmentChartsBinding::bind)

    override val vm: ChartsViewModel by viewModel {
        parametersOf(getParams(ChartsFeature.ChartsParams::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.buildChart()
        binding.setDate.setOnClickListener {
            vm.openDatePicker()
        }
    }

    override fun renderState(state: ChartsState) {
        val valueFormatterValue: ValueFormatter
        with(binding) {
            xAxisFormatter.setDates(state.listDates)

            val dataSet = LineDataSet(state.data, "Graph")
            with(dataSet) {
                mode = LineDataSet.Mode.LINEAR
                setDrawFilled(true)
                setDrawCircleHole(false)
                lineWidth = 1.0f
                setCircleColor(getColor(requireContext(), state.pointColor))
                highLightColor = this@ChartsFragment.resources.getColor(R.color.gray1)
                color = getColor(requireContext(), state.graphColor)
                fillAlpha = 20
                setDrawHorizontalHighlightIndicator(false)
                fillFormatter = IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }
            }

            when (state.deviceTypes) {
                SensorType.TemperatureSensor -> {
                    valueFormatterValue = SensorValueFormatter("°C")
                    title.text = getString(R.string.chart_graph_temp)
                }

                SensorType.HumiditySensor -> {
                    valueFormatterValue = SensorValueFormatter("%")
                    title.text = getString(R.string.chart_graph_hum)
                }

                SensorType.PressureSensor -> {
                    valueFormatterValue = SensorValueFormatter("Па")
                    title.text = getString(R.string.chart_graph_pressure)
                }
                else -> {
                    valueFormatterValue = SensorValueFormatter("")
                }
            }


            with(chart) {
                axisLeft.valueFormatter = valueFormatterValue
                setTouchEnabled(false)
                legend.textColor = this.resources.getColor(R.color.gray1)
                legend.isEnabled = false
                description.isEnabled = false
                setTouchEnabled(false)
                getPaint(Chart.PAINT_INFO).textSize = 8F.dp
                xAxis.apply {
                    setupEnvironments(null, 8F, requireContext())
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = xAxisFormatter
                }
                axisLeft.setupEnvironments(null, 8F, requireContext())
                axisRight.isEnabled = false
                extraBottomOffset = 16F
                invalidate()

                val chartData = LineData(dataSet)
                chartData.setValueTextSize(8f)
                chartData.setDrawValues(false)

                data = chartData

                animateX(500)
            }
        }
    }

    override fun handleEvent(event: ChartsEvent) {
        when (event) {
            is ChartsEvent.OpenDatePicker -> {
                val date = Calendar.getInstance()
                DatePickerDialog(
                    requireContext(),
                    AlertDialog.THEME_HOLO_LIGHT,
                    { _, year, month, day ->
                        vm.buildChart("${day.toTime()}-${month.inc().toTime()}-$year")
                    },
                    date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH),
                    date.get(Calendar.DAY_OF_MONTH)
                )
                    .show()
            }
        }
    }

}