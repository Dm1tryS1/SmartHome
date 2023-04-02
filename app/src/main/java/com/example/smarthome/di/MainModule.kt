package com.example.smarthome.di

import com.example.smarthome.fragments.main.MainViewModel
import com.example.smarthome.repository.DeviceInfoDataBaseRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {
    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { MainViewModel(get()) }
    }

    private fun createDomainModule() = module {

    }

    private fun createDataModule() = module {
        factory { DeviceInfoDataBaseRepository(get()) }
    }

}