package com.example.smarthome.fragments.information.dialog

import android.app.Dialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuSensorBinding
import com.example.smarthome.utils.BottomSheetDialogBuilder

object Sensor {
    fun create(
        fragment: Fragment,
        action: (aPackage: Pair<Int, Int>) -> Unit,
        resources: Int,
        command: Pair<Int,Int>,
        data: String,
        date: String,
    ): Dialog {
        val binding = DropmenuSensorBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            value.text = data
            this.date.text = date


            icon.setImageDrawable(
                ContextCompat.getDrawable(
                    fragment.requireContext(),
                    resources
                )
            )

            update.setOnClickListener {
                action(command)
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}