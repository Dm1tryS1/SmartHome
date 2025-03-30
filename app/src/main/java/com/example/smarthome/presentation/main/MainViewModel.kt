package com.example.smarthome.presentation.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.example.account_impl.presentation.AccountFragment
import com.example.core.presentation.BaseViewModel
import com.example.home_impl.presentation.HomeFragment
import com.example.settings_impl.presentation.SettingsFragment
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(router: Router): BaseViewModel<MainState, MainEvent>(router = router) {

    private val fragmentsStack = Stack<TabType>()

    private var onBackWasPressed = false

    init {
        fragmentsStack.add(TabType.Home)
    }

    override fun createInitialState(): MainState {
        return MainState(currentTab = fragmentsStack.peek())
    }

    fun onShowFragment(tab: TabType) {
        if (fragmentsStack.peek() == tab) {
            return
        }

        fragmentsStack.remove(tab)
        fragmentsStack.push(tab)

        updateState {
            it.copy(
                currentTab = tab,
            )
        }
    }

    fun buildFragment(type: TabType): Fragment {
        return when (type) {
            TabType.Home -> HomeFragment()
            TabType.Account -> AccountFragment()
        }
    }

    override fun onBackPressed(): Boolean {
        onBackWasPressed = !onBackWasPressed
        sendEvent(MainEvent.ShowSnack)
        viewModelScope.launch {
            delay(3000)
            onBackWasPressed = false
        }
        return onBackWasPressed
    }

}