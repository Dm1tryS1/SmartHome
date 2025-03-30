package com.example.information_impl.presentation.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.core.utils.BottomSheetDialogBuilder
import com.example.data.device.ConditionerCommands
import com.example.information_impl.databinding.DropmenuConditioenerBinding

object Conditioner {
    fun create(
        fragment: Fragment,
        action: (command: String) -> Unit,
        on: Boolean
        ): Dialog {
        val binding = DropmenuConditioenerBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            offOn.setOnClickListener {
                if (!on) {
                    action.invoke(ConditionerCommands.On.command)
                } else {
                    action.invoke(ConditionerCommands.Off.command)
                }
                dialog.dismiss()
            }

            reduce.setOnClickListener {
                action.invoke(ConditionerCommands.ReduceTemperature.command)
            }

            add.setOnClickListener {
                action.invoke(ConditionerCommands.AddTemperature.command)
            }

            return dialog.build()
        }
    }
}