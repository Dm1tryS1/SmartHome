package com.example.smarthome.fragments.information

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.fragments.information.recyclerView.mapper.packageToInfoViewItem
import com.example.smarthome.utils.Command
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InformationViewModel(private val informationInteractor: InformationInteractor) : ViewModel() {

    val state = MutableLiveData(InformationState(listOf()))
    val event = MutableLiveData<InformationEvent>()

    fun initializeState() {
        sendPackage(Command.BroadCast.command)
    }

    fun sendPackage(aPackage: Pair<Int, Int>) {
        informationInteractor.sendPackage(aPackage)
    }

    fun getInfo() {
        viewModelScope.launch {
            informationInteractor.getInfo().collectLatest {
                state.value?.let { informationState ->
                    if (informationState.data != null)
                        informationState.data.let { currentState ->
                            val sensor = currentState.find { item ->
                                    item.id == it.id
                            }

                            val newState = if (sensor == null) {
                                currentState + packageToInfoViewItem(it)
                            } else
                                currentState.map { item ->
                                    if (item.id == it.id)
                                        packageToInfoViewItem(it)
                                    else
                                        item
                                }
                            state.postValue(InformationState(newState.sortedBy {
                                it.sensorType.type
                            }))
                        }
                    else
                        state.postValue(InformationState(listOf(packageToInfoViewItem(it))))
                }

            }
        }
    }

    fun onMenuClicked(id: Int, info: String, date: String) {
        when (id) {
            1, 3 -> event.postValue(InformationEvent.OpenTemperatureMenuEvent(info, date))
            2 -> event.postValue(InformationEvent.OpenConditionerMenuEvent)
            5, 6 -> event.postValue(InformationEvent.OpenHumidityMenuEvent(info, date))
            4 -> event.postValue(InformationEvent.OpenHumidifierMenuEvent)
            else -> {}
        }
    }

    fun onSettingsClicked() {
        informationInteractor.getUserSettings {
            event.postValue(InformationEvent.OpenSettingsMenuEvent(it))
        }
    }

    fun saveUserSettings(value: Int) {
        informationInteractor.saveUserSettings(value)
    }
}