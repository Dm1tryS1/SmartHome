package com.example.smarthome.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.data.DeviceInfoSchema
import com.example.data.getTime
import com.example.information_impl.data.mapper.getAllResponseMapper
import com.example.information_impl.data.model.GetAllResponse
import com.example.shared_preferences.SharedPreferences
import com.example.storage.entity.DeviceInfo
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

class WiFiService : Service() {

    private val deviceInfoDataBaseRepository: DeviceInfoDataBaseRepository by inject()
    private val sharedPreferencesRepository: SharedPreferences by inject()

    private var receiveThread: ReceiveThread? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        receiveThread = ReceiveThread()
        receiveThread?.start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun saveInDataBase(deviceInfoSchema: DeviceInfoSchema) {
        if (deviceInfoSchema is DeviceInfoSchema.Sensors) {
            deviceInfoDataBaseRepository.saveDeviceInfo(
                DeviceInfo(
                    deviceId = deviceInfoSchema.id,
                    time = "${deviceInfoSchema.hours}:${deviceInfoSchema.minutes}",
                    value = deviceInfoSchema.data,
                    date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                )
            )
        }
    }

    private fun parser(json: String) {
        for (item in getAllResponseMapper(
            Gson().fromJson(json, GetAllResponse::class.java),
            getTime()
        )) {
            saveInDataBase(item)
        }
    }

    override fun onDestroy() {
        Log.d("12345", "Destroyed")
        super.onDestroy()
    }

    private fun receiveData() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {
                    val msg = ByteArray(1024)
                    val ip =
                        sharedPreferencesRepository.getString(SharedPreferences.systemIp)
                            ?: ""
                    if (ip.isEmpty()) continue
                    val socket = Socket(ip, 81)
                    socket.soTimeout = 1000
                    val input = socket.getInputStream()
                    input.read(msg)
                    Log.d(
                        "12345",
                        String(msg.filter { it != 0.toByte() }.toByteArray(), Charsets.UTF_8)
                    )
                    socket.close()
                    parser(String(msg.filter { it != 0.toByte() }.toByteArray(), Charsets.UTF_8))
                } catch (_: Exception) {
                    continue
                }
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    inner class ReceiveThread : Thread() {
        override fun run() {
            receiveData()
        }
    }
}