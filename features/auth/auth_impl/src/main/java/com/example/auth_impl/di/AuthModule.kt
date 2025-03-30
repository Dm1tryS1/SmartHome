package com.example.auth_impl.di


import com.example.auth_api.AuthFeature
import com.example.auth_impl.AuthFeatureImpl
import com.example.auth_impl.presentation.AuthViewModel
import com.example.auth_impl.domain.AuthUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object AuthModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory { AuthFeatureImpl() } bind AuthFeature::class
    }


    private fun createPresentationModule() = module {
        viewModel { AuthViewModel(get(), get()) }
    }

    private fun createDomainModule() = module {
        factory { AuthUseCase(get()) }
    }

    private fun createDataModule() = module {
    }

}