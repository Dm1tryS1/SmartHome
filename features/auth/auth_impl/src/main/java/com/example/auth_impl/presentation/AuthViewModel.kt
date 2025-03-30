package com.example.auth_impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.auth_impl.presentation.domain.AuthUseCase
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authUseCase: AuthUseCase,
    router: Router,
) : BaseViewModel<AuthState, AuthEvent>(router = router) {

    fun auth(login: String, password: String) {
        updateState { state ->
            state.copy(isLoading = true, error = null)
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (login.isNotBlank() && password.isNotBlank()) {
                if (authUseCase(login, password)) {
                    router.exit()
                } else {
                    updateState { state ->
                        state.copy(error = "Неверные логин или пароль")
                    }
                }
            } else {
                updateState { state ->
                    state.copy(error = "Введите логин и пароль")
                }
            }
            updateState { state ->
                state.copy(isLoading = false)
            }
        }
    }

    override fun createInitialState(): AuthState {
        return AuthState(isLoading = false, error = null)
    }
}