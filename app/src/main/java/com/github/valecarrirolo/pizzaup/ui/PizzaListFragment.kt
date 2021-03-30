package com.github.valecarrirolo.pizzaup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import com.github.valecarrirolo.pizzaup.MainAdapter
import com.github.valecarrirolo.pizzaup.MainViewModel
import com.github.valecarrirolo.pizzaup.databinding.FragmentPizzalistBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class PizzaListFragment : Fragment() {
    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPizzalistBinding.inflate(inflater)
        .also { binding ->
            // Progress Bar Loading
            viewModel.isLoading.observe { isLoading: Boolean ->
                binding.progressLoader.isVisible = isLoading // == true
            }

            // RecyclerView List
            val adapter = MainAdapter(viewModel)
            binding.recyclerViewExample.adapter = adapter
            viewModel.allPizzas.observe { pizzaList ->
                adapter.dataSet = pizzaList
                adapter.notifyDataSetChanged()

            }
        }.root

    // <T> replaces the boolean to make the function universal
    private fun <T> Flow<T>.observe(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        action: suspend (T) -> Unit
    ) {
        viewLifecycleOwner.addRepeatingJob(state) {
            collect(action)
        }
    }
}
