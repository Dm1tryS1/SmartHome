package com.example.smarthome.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.FragmentHomeBinding
import com.example.smarthome.main.MainActivity
import com.example.smarthome.main.Screen
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val vm: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.informationMode.setOnClickListener {
            (activity as MainActivity).navigate(Screen.Information)
        }
    }
}