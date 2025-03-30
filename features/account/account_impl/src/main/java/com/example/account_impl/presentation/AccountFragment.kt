package com.example.account_impl.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.example.account_impl.presentation.dialog.Settings
import com.example.account_impl.presentation.recyclerView.adapter.GroupsAdapter
import com.example.core.fragmentViewBinding
import com.example.core.presentation.BaseFragment
import com.example.core.utils.supportBottomSheetScroll
import com.example.settings_impl.R
import com.example.settings_impl.databinding.FragmentAccountBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<AccountState, AccountEvent>(R.layout.fragment_account) {

    private val binding by fragmentViewBinding(FragmentAccountBinding::bind)

    override val vm: AccountViewModel by viewModel()

    private val adapter =
        GroupsAdapter(
            onMenuClicked = { vm.connectSystem() },
            onItemClicked = { vm.onGroupClicked(it) }
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        groups.adapter = adapter
        groups.supportBottomSheetScroll()

        auth.setOnClickListener {
            vm.auth()
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

        binding.groups.isGone = state.groups.isEmpty() || !state.isAuth
        binding.emptyTitle.isGone = state.groups.isNotEmpty() || !state.isAuth

        adapter.items = state.groups
    }

    override fun handleEvent(event: AccountEvent) = when(event) {
        is AccountEvent.OpenGroupMenu -> {
            Settings.create(
                fragment = this@AccountFragment,
                action = event.onConnect,
                group = event.group
            ).show()
        }
    }
}