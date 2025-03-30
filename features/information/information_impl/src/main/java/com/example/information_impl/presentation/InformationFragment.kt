package com.example.information_impl.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import com.example.core.presentation.BaseFragment
import com.example.data.device.SensorType
import com.example.core.fragmentViewBinding
import com.example.core.utils.supportBottomSheetScroll
import com.example.information_impl.R
import com.example.information_impl.databinding.FragmentInformationBinding
import com.example.information_impl.presentation.dialog.Conditioner
import com.example.information_impl.presentation.dialog.Humidifier
import com.example.information_impl.presentation.dialog.Sensor
import com.example.information_impl.presentation.recyclerView.adapter.InformationAdapter
import com.example.information_impl.presentation.recyclerView.model.InfoViewItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationFragment : BaseFragment<InformationState, InformationEvent>(R.layout.fragment_information) {

    private val binding by fragmentViewBinding(FragmentInformationBinding::bind)

    override val vm: InformationViewModel by viewModel()
    private val adapter =
        InformationAdapter(onMenuClicked = { deviceInfo ->
            vm.onMenuClicked(deviceInfo)
        }, onDeviceClicked = { type, id ->
            vm.onChartOpen(type, id)
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        sensors.adapter = adapter
        sensors.supportBottomSheetScroll()

        reloadButton.setOnClickListener {
            vm.getInfo()
        }

        settings.setOnClickListener {
            vm.onSettingsClicked()
        }

        vm.getInfo()
    }

    private fun makeNotification(id: Int, text: String) {
        val builder = NotificationCompat.Builder(requireContext(), CHANEL_ID)
            .setSmallIcon(R.drawable.ic_smart_home)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(text)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANEL_ID,
                    CHANEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        notificationManager.notify(id, builder.build())
    }

    override fun renderState(state: InformationState) {
        if (state.data != null) {
            binding.sensors.isVisible = true

            var currentType = SensorType.Unknown
            val items = mutableListOf<InfoViewItem>()
            state.data.forEach {
                if (currentType != it.sensorType) {
                    currentType = it.sensorType
                    items.add(InfoViewItem.Header(it.sensorType.text))
                }
                items.add(it)
            }
            adapter.items = items
            binding.loader.isVisible = state.isLoading

            if(!state.isLoading && items.isEmpty()) {
                binding.icon.isVisible = true
                binding.title.isVisible = true
            } else {
                binding.icon.isVisible = false
                binding.title.isVisible = false
            }
        } else {
            binding.sensors.isVisible = false
            binding.icon.isVisible = true
            binding.title.isVisible = true
        }
    }

    override fun handleEvent(event: InformationEvent) {
        when (event) {
            is InformationEvent.OpenSensorMenuEvent -> {
                Sensor.create(
                    fragment = this@InformationFragment,
                    action = event.command,
                    deviceInfo = event.deviceInfo
                ).show()
            }
            is InformationEvent.OpenConditionerMenuEvent -> {
                Conditioner.create(
                    fragment = this@InformationFragment,
                    action = event.command,
                    on = event.on
                ).show()
            }
            is InformationEvent.OpenHumidifierMenuEvent -> {
                Humidifier.create(
                    fragment = this@InformationFragment,
                    action = event.command,
                    on = event.on
                ).show()
            }
            is InformationEvent.ShowNotification -> {
                val text = if (event.type == SensorType.TemperatureSensor) {
                    if (event.more) {
                        getString(R.string.notification_max_temp, event.id, event.comfortableValue)
                    } else {
                        getString(R.string.notification_min_temp, event.id, event.comfortableValue)
                    }
                } else {
                    if (event.more) {
                        getString(R.string.notification_max_hum, event.id, event.comfortableValue)
                    } else {
                        getString(R.string.notification_min_hum, event.id, event.comfortableValue)
                    }
                }
                makeNotification(event.id, text)
            }
        }
    }

    companion object {
        const val CHANEL_ID = "CHANNEL_ID"
    }
}