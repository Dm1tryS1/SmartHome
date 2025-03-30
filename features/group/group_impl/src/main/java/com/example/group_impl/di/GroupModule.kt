package com.example.group_impl.di

import com.example.group_api.GroupFeature
import com.example.group_impl.GroupFeatureImpl
import com.example.group_impl.presentation.GroupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object GroupModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory { GroupFeatureImpl() } bind GroupFeature::class
    }


    private fun createPresentationModule() = module {
        viewModel { GroupViewModel(get(), get()) }

        factory { GroupViewModel.Features(get()) }
    }

    private fun createDomainModule() = module {
    }

    private fun createDataModule() = module {
    }

}