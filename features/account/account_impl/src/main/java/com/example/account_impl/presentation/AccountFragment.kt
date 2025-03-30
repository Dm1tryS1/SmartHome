package com.example.account_impl.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.example.core.fragmentViewBinding
import com.example.core.presentation.BaseFragment
import com.example.settings_impl.R
import com.example.settings_impl.databinding.FragmentAccountBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<AccountState, AccountEvent>(R.layout.fragment_account) {

    private val binding by fragmentViewBinding(FragmentAccountBinding::bind)

    override val vm: AccountViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        auth.setOnClickListener {
            vm.auth()
        }

        item1.dropdownMenu.setOnClickListener {
            vm.connectSystem()
        }

        createGroup.setOnClickListener {
            vm.addGroup()
        }
    }

    override fun onResume() {
        super.onResume()
        vm.checkAuth()
    }

    override fun renderState(state: AccountState) {
        binding.authState.isGone = state.isAuth

        binding.groups.isGone = !state.hasGroups || !state.isAuth
        binding.emptyTitle.isGone = state.hasGroups || !state.isAuth
    }

    override fun handleEvent(event: AccountEvent) = Unit
}