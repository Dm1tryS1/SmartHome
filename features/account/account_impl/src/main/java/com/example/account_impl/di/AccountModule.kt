package com.example.account_impl.di


import com.example.account_impl.AccountFeatureImpl
import com.example.account_impl.presentation.AccountViewModel
import com.example.account_impl.domain.AccountUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object AccountModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory { AccountFeatureImpl() } bind AccountFeatureImpl::class
    }


    private fun createPresentationModule() = module {
        viewModel { AccountViewModel(get(), get(), get()) }

        factory { AccountViewModel.Features(get(), get()) }
    }

    private fun createDomainModule() = module {
        factory { AccountUseCase(get()) }
    }

    private fun createDataModule() = module {
    }
}