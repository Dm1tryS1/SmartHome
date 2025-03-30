package com.example.auth_impl.presentation

import android.os.Bundle
import android.view.View
import com.example.auth_impl.R
import com.example.auth_impl.databinding.FragmentAuthBinding
import com.example.core.fragmentViewBinding
import com.example.core.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment<AuthState, AuthEvent>(R.layout.fragment_auth) {

    private val binding by fragmentViewBinding(FragmentAuthBinding::bind)

    override val vm: AuthViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        auth.setOnClickListener {
            vm.auth(login.text.toString(), password.text.toString())
        }
    }

    override fun renderState(state: AuthState) = Unit

    override fun handleEvent(event: AuthEvent) = Unit
}