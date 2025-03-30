package com.example.auth_impl.presentation.domain

import com.example.shared_preferences.SharedPreferences
import kotlinx.coroutines.delay

class AuthUseCase(
    private val sharedPreferencesRepository: SharedPreferences,
) {
    suspend operator fun invoke(login: String, password: String): Boolean {
        delay(1000)
        return (login == "dvm101" && password == "12345")
            .also {
                sharedPreferencesRepository.saveString(SharedPreferences.authToken, login)
            }
    }
}