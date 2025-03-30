package com.example.connection_impl.presentation.remote_control

import androidx.lifecycle.viewModelScope
import com.example.connection_impl.R
import com.example.connection_impl.domain.RemoteControlUseCase
import com.example.core.presentation.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch

class RemoteControlViewModel(
    router: Router,
    private val remoteControlUseCase: RemoteControlUseCase
) : BaseViewModel<RemoteControlState, RemoteControlEvent>(router = router) {


    override fun createInitialState(): RemoteControlState {
        return RemoteControlState(Type.TypeCond)
    }

    fun changeList(type: Type) {
        when (type) {
            Type.TypeCond -> updateState { RemoteControlState(Type.TypeCond) }
            Type.TypeHum -> updateState { RemoteControlState(Type.TypeHum) }
        }
    }

    fun writeCommandForRemoteControl(deviceType: Int, command: String) {
        viewModelScope.launch {
            updateState { state -> state.copy(isLoading = true) }
            val result = remoteControlUseCase.writeCommandForRemoteControl(deviceType, command).data
            if (result != null && result.result == "success") {
                updateState { state -> state.copy(isLoading = false) }
                sendEvent(RemoteControlEvent.ShowToast(R.string.remote_control_success))
            } else {
                updateState { state -> state.copy(isLoading = false) }
                sendEvent(RemoteControlEvent.ShowToast(R.string.remote_control_error))
            }
        }
    }

    enum class Type {
        TypeCond, TypeHum
    }

}