package com.example.information_impl.presentation.dialog

import android.app.Dialog
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.core.utils.BottomSheetDialogBuilder
import com.example.information_impl.databinding.DropmenuSettingsBinding

object Settings {
    fun create(
        fragment: Fragment,
        action: (value: Int) -> Unit,
        save: (value: Int) -> Unit,
        openSystemSettings: () -> Unit,
        progress: Int = 0
    ): Dialog {
        val binding = DropmenuSettingsBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            close.setOnClickListener {
                dialog.dismiss()
            }


            date.progress = progress
            minute.text = if (progress == 0)
                "Выключено"
            else
                (progress*5).toString()

            date.setOnSeekBarChangeListener(Listener(binding))

            update.setOnClickListener {
                action.invoke(date.progress * 5)
                save(date.progress)
                dialog.dismiss()
            }

            more.setOnClickListener {
                openSystemSettings()
            }

            return dialog.build()
        }
    }

    class Listener(val binding: DropmenuSettingsBinding) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            if (p1 != 0)
                binding.minute.text = (p1 * 5).toString()
            else
                binding.minute.text = "Выключено"
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }
}