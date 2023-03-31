package com.example.smarthome.di

import com.example.smarthome.fragments.connectDevice.chooseDevice.ChooseDeviceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ChooseDeviceModule {

    operator fun invoke() = listOf(
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createPresentationModule() = module {
        viewModel { parameters -> ChooseDeviceViewModel(get(), chooseDeviceParams = parameters.get(), get()) }
    }

    private fun createDomainModule() = module {

    }

    private fun createDataModule() = module {
    }
}