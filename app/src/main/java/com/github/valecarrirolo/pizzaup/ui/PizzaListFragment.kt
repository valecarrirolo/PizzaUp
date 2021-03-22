package com.github.valecarrirolo.pizzaup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.github.valecarrirolo.pizzaup.MainAdapter
import com.github.valecarrirolo.pizzaup.MainViewModel
import com.github.valecarrirolo.pizzaup.databinding.FragmentPizzalistBinding

class PizzaListFragment : Fragment() {
    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPizzalistBinding.inflate(inflater)
        .also { binding ->
            // Progress Bar Loading
            viewModel.isLoading.asLiveData().observe(viewLifecycleOwner) { isLoading: Boolean ->
                binding.progressLoader.isVisible = isLoading // == true
            }

            // RecyclerView List
            val adapter = MainAdapter(viewModel)
            binding.recyclerViewExample.adapter = adapter
            viewModel.allPizzas.asLiveData().observe(viewLifecycleOwner) { pizzaList ->
                adapter.dataSet = pizzaList
                adapter.notifyDataSetChanged()
            }
        }
        .root
}
