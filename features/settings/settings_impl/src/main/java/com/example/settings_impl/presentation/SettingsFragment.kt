package com.example.settings_impl.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.core.presentation.BaseFragment
import com.example.core.fragmentViewBinding
import com.example.core.snackBar
import com.example.settings_impl.R
import com.example.settings_impl.databinding.FragmentSettingsBinding
import com.example.settings_impl.presentation.dialog.Connection
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<SettingsState, SettingsEvent>(R.layout.fragment_settings) {

    private val binding by fragmentViewBinding(FragmentSettingsBinding::bind)

    override val vm: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        connect.setOnClickListener {
            vm.onConnectClicked()
        }

        wifi.setOnClickListener {
            vm.onWifiClicked()
        }
    }

    override fun renderState(state: SettingsState) {
        binding.loader.isVisible = state.isLoading
        binding.icon.isVisible = !state.isLoading
        binding.title.isVisible = !state.isLoading
    }

    override fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ShowSnack -> snackBar(getString(event.message))
            is SettingsEvent.OpenDialog -> Connection.create(
                fragment = this@SettingsFragment,
                connectAction = vm::connect,
                wifiInfo = event.wifiInfo
            ).show()
        }
    }
}