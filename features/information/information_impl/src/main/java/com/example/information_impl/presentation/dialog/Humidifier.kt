package com.example.information_impl.presentation.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.core.utils.BottomSheetDialogBuilder
import com.example.data.device.HumidifierCommands
import com.example.information_impl.databinding.DropmenuHumidifierBinding

object Humidifier {
    fun create(
        fragment: Fragment,
        action: (command: String) -> Unit,
        on: Boolean
    ): Dialog {
        val binding = DropmenuHumidifierBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            offOn.setOnClickListener {
                if (!on) {
                    action.invoke(HumidifierCommands.On.command)
                } else {
                    action.invoke(HumidifierCommands.Off.command)
                }
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}