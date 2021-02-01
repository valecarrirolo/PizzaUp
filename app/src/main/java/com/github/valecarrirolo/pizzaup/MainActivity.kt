package com.github.valecarrirolo.pizzaup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.github.valecarrirolo.pizzaup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Image loading
        // binding.imageExample.load("https://raw.githubusercontent.com/nemsi85/dev-server/master/pizza.jpg")

        // Progress Bar Loading
        viewModel.isLoading.asLiveData().observe(this) { isLoading: Boolean ->
            binding.progressLoader.isVisible = isLoading // == true
        }

        // RecyclerView List
        val adapter = MainAdapter(viewModel)
        binding.recyclerViewExample.adapter = adapter
        viewModel.currentPizzas.asLiveData().observe(this) { pizzaList ->
            adapter.dataSet = pizzaList
            adapter.notifyDataSetChanged()
        }

        // Button and Observe on isFiltered()
        viewModel.isFiltered.asLiveData().observe(this) { isFiltered: Boolean ->
            binding.buttonRecap.text = if (isFiltered) "Lista completa" else "Pizze ordinate"
        }
        binding.buttonRecap.setOnClickListener {
            viewModel.recapPizza()
        }

        // Message No Pizza Selected / Empty list
        viewModel.isPizzasEmpty.asLiveData().observe(this) {isPizzasEmpty: Boolean ->
            binding.noneSelected.isVisible = isPizzasEmpty
        }
    }
}