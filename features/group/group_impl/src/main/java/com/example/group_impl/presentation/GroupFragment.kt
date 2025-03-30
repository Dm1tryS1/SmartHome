package com.example.group_impl.presentation

import android.os.Bundle
import android.view.View
import com.example.core.fragmentViewBinding
import com.example.core.presentation.BaseFragment
import com.example.group_impl.R
import com.example.group_impl.databinding.FragmentGroupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupFragment : BaseFragment<GroupState, GroupEvent>(R.layout.fragment_group) {

    private val binding by fragmentViewBinding(FragmentGroupBinding::bind)

    override val vm: GroupViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        next.setOnClickListener {
            vm.createGroup()
        }
    }

    override fun renderState(state: GroupState) = Unit

    override fun handleEvent(event: GroupEvent) = Unit
}