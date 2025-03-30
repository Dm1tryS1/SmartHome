package com.example.information_impl.presentation.dialog

import android.app.Dialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.data.device.SensorType
import com.example.core.utils.BottomSheetDialogBuilder
import com.example.information_impl.databinding.DropmenuSensorBinding
import com.example.information_impl.presentation.recyclerView.model.InfoViewItem

object Sensor {
    fun create(
        fragment: Fragment,
        action: (id: Int, sensorType: SensorType) -> Unit,
        deviceInfo: InfoViewItem.SensorsInfoViewItem
    ): Dialog {
        val binding = DropmenuSensorBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            value.text = deviceInfo.info
            this.date.text = deviceInfo.date


            icon.setImageDrawable(
                ContextCompat.getDrawable(
                    fragment.requireContext(),
                    deviceInfo.iconId
                )
            )

            update.setOnClickListener {
                action(deviceInfo.id, deviceInfo.sensorType)
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}