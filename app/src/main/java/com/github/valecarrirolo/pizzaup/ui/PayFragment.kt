package com.github.valecarrirolo.pizzaup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.valecarrirolo.pizzaup.MainViewModel
import com.github.valecarrirolo.pizzaup.databinding.FragmentPayBinding

class PayFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //View Binding
        val binding = FragmentPayBinding.inflate(inflater)
        return binding.root
    }
}