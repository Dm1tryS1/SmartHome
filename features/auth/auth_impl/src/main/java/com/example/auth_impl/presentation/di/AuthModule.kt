package com.example.auth_impl.presentation.di


import com.example.auth_impl.AuthFeatureImpl
import com.example.auth_impl.presentation.AuthViewModel
import com.example.auth_impl.presentation.domain.AuthUseCase
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
        factory { AuthFeatureImpl() } bind AuthFeatureImpl::class
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