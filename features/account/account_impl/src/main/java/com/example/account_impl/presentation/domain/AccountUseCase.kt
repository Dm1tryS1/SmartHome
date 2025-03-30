package com.example.account_impl.presentation.domain

import com.example.shared_preferences.SharedPreferences
import kotlinx.coroutines.delay

class AccountUseCase(
    private val sharedPreferencesRepository: SharedPreferences,
) {
    operator fun invoke(): Boolean =
        !sharedPreferencesRepository.getString(SharedPreferences.authToken).isNullOrEmpty()
}