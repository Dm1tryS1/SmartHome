package com.example.smarthome.fragments.connectDevice.chooseDevice.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuConnectWifiDeviceBinding
import com.example.smarthome.common.wifi.WifiInfo
import com.example.smarthome.core.utils.BottomSheetDialogBuilder

object Connection {
    fun create(
        type: Int,
        id: Int,
        fragment: Fragment,
        wifiInfo: WifiInfo,
        connectAction: (type: Int, id: Int, wifiInfo: WifiInfo) -> Unit,
    ): Dialog {
        val binding = DropmenuConnectWifiDeviceBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            title.text = "${title.text} ${wifiInfo.ssid}"

            connect.setOnClickListener {
                connectAction(type, id, wifiInfo.copy(password = password.text.toString()))
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}