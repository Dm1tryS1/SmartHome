package com.example.shared_preferences

import android.content.Context

class SharedPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(
        sharedPreferenceName,
        Context.MODE_PRIVATE
    )

    fun getInt(key: String) = preferences.getInt(key, -1)

    fun getString(key: String) = preferences.getString(key, "")

    fun saveInt(key: String, value: Int) {
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun saveString(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun deleteUserSettings(key: String) {
        val editor = preferences.edit()
        editor.remove(key)
        editor.apply()
    }

    companion object {
        const val sharedPreferenceName = "SmartHomeStorage"
        const val userTimer = "UserTimer"
        const val userMaxTemperature = "UserMaxTemperature"
        const val userMinTemperature = "UserMinTemperature"
        const val userMaxHumidity = "UserMaxHumidity"
        const val userMinHumidity = "UserMinHumidity "
        const val userDisplayedValue = "UserDisplayedValue"
        const val systemIp = "SystemIp"
        const val authToken = "AuthToken"
    }
}