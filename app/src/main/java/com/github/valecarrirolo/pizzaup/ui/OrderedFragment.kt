package com.github.valecarrirolo.pizzaup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.github.valecarrirolo.pizzaup.MainAdapter
import com.github.valecarrirolo.pizzaup.MainViewModel
import com.github.valecarrirolo.pizzaup.R
import com.github.valecarrirolo.pizzaup.databinding.FragmentOrderedBinding
import com.github.valecarrirolo.pizzaup.databinding.FragmentPizzalistBinding
import com.github.valecarrirolo.pizzaup.formatPrice

class OrderedFragment : Fragment() {
    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //View Binding
        val binding = FragmentOrderedBinding.inflate(inflater)

        // Progress Bar Loading
        viewModel.isLoading.asLiveData().observe(viewLifecycleOwner) { isLoading: Boolean ->
            binding.progressLoader.isVisible = isLoading // == true
        }

        // Message No Pizza Selected / Empty list
        viewModel.isPizzasEmpty.asLiveData().observe(viewLifecycleOwner) {isPizzasEmpty: Boolean ->
            binding.noneSelected.isVisible = isPizzasEmpty
        }

        // RecyclerView List
        val adapter = MainAdapter(viewModel)
        binding.recyclerViewExample.adapter = adapter
        viewModel.orderedPizzas.asLiveData().observe(viewLifecycleOwner) { pizzaList ->
            adapter.dataSet = pizzaList
            adapter.notifyDataSetChanged()
        }

        //Total Cost ordered pizzas
        viewModel.totalCost.asLiveData().observe(viewLifecycleOwner){
            binding.totalCost.text = formatPrice(it)
        }

        return binding.root
    }
}