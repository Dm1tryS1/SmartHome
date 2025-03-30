package com.example.smarthome.di

import com.example.account_impl.presentation.di.AccountModule
import com.example.auth_impl.presentation.di.AuthModule
import com.example.chart_impl.di.ChartsModule
import com.example.connection_impl.di.ConnectionModule
import com.example.core.navigation.NavigationModule
import com.example.home_impl.di.HomeModule
import com.example.information_impl.di.InformationModule
import com.example.network.di.NetworkModule
import com.example.settings_impl.presentation.di.SettingsModule
import com.example.shared_preferences.di.SharedPreferencesModule
import com.example.storage.di.StorageModule
import com.example.system_impl.di.SystemModule
import org.koin.core.module.Module

object AppModule {
    operator fun invoke() = listOf(
        createModules()
    ).flatten()

    private fun createModules(): List<Module> = listOf(
        HomeModule(),
        SettingsModule(),
        InformationModule(),
        NavigationModule(),
        MainModule(),
        ChartsModule(),
        SystemModule(),
        ConnectionModule(),
        NetworkModule(),
        SharedPreferencesModule(),
        StorageModule(),
        AccountModule(),
        AuthModule(),
    ).flatten()
}