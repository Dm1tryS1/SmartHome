package com.example.system_impl.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.core.presentation.BaseFragment
import com.example.core.fragmentViewBinding
import com.example.system_impl.R
import com.example.system_impl.databinding.FragmentSystemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SystemFragment : BaseFragment<SystemState, Unit>(R.layout.fragment_system) {

    private val binding by fragmentViewBinding(FragmentSystemBinding::bind)
    override val vm: SystemViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            minTemp.text.setText(R.string.system_min_temp)
            maxTemp.text.setText(R.string.system_max_temp)
            minHum.text.setText(R.string.system_min_humidity)
            maxHum.text.setText(R.string.system_max_humidity)

            minTemp.numberPicker.apply {
                minValue = MIN_TEMP_VALUE
                maxValue = MAX_TEMP_VALUE
                setOnValueChangedListener { _, _, value ->
                    maxTemp.numberPicker.minValue = value
                }
            }

            maxTemp.numberPicker.apply {
                minValue = MIN_TEMP_VALUE
                maxValue = MAX_TEMP_VALUE
                setOnValueChangedListener { _, _, value ->
                    minTemp.numberPicker.maxValue = value
                }
            }

            minHum.numberPicker.apply {
                minValue = MIN_HUM_VALUE
                maxValue = MAX_HUM_VALUE
                setOnValueChangedListener { _, _, value ->
                    maxHum.numberPicker.minValue = value
                }
            }

            maxHum.numberPicker.apply {
                minValue = MIN_HUM_VALUE
                maxValue = MAX_HUM_VALUE
                setOnValueChangedListener { _, _, value ->
                    minHum.numberPicker.maxValue = value
                }
            }

            save.setOnClickListener {
                vm.save(
                    maxTemp.numberPicker.value,
                    minTemp.numberPicker.value,
                    maxHum.numberPicker.value,
                    minHum.numberPicker.value,
                    when (radioGroup.checkedRadioButtonId) {
                        R.id.btn_max_temp -> 0
                        R.id.btn_min_temp -> 1
                        R.id.btn_max_hum -> 2
                        R.id.btn_min_hum -> 3
                        else -> 4
                    }
                )
            }
            cancel.setOnClickListener {
                vm.clear()
            }
        }
    }

    override fun renderState(state: SystemState) {
        with(binding) {
            maxTemp.numberPicker.value = state.maxTemp
            minTemp.numberPicker.value = state.minTemp
            minHum.numberPicker.value = state.minHum
            maxHum.numberPicker.value = state.maxHum
            radioGroup.check(
                when (state.displayedValue) {
                    0 -> R.id.btn_max_temp
                    1 -> R.id.btn_min_temp
                    2 -> R.id.btn_max_hum
                    else -> R.id.btn_min_hum
                }
            )
            loader.isVisible = state.isLoading
        }

    }

    override fun handleEvent(event: Unit) {}

    companion object {
        const val MIN_TEMP_VALUE = 18
        const val MAX_TEMP_VALUE = 28
        const val MIN_HUM_VALUE = 30
        const val MAX_HUM_VALUE = 60
        const val DISPLAYED_VALUE = 4
    }
}