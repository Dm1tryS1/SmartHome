package com.example.account_impl.presentation.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.account_impl.presentation.recyclerView.model.GroupViewItem
import com.example.core.utils.BottomSheetDialogBuilder
import com.example.settings_impl.databinding.GroupDropmenuSettingsBinding

object Settings {
    fun create(
        fragment: Fragment,
        action: (value: Int) -> Unit,
        group: GroupViewItem.Group,
    ): Dialog {
        val binding = GroupDropmenuSettingsBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            status.isSelected = group.status
            status.text = if (group.status) {
                "Подключено"
            } else {
                "Не подключено"
            }

            connectToGroup.setOnClickListener {
                action(group.id)
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}