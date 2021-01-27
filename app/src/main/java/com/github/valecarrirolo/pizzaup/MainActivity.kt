package com.github.valecarrirolo.pizzaup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import coil.load
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

        // RecyclerView List
        val adapter = MainAdapter(viewModel)
        binding.recyclerViewExample.adapter = adapter
        viewModel.filteredPizza.asLiveData().observe(this) { pizzaList ->
            adapter.dataSet = pizzaList
            adapter.notifyDataSetChanged()
        }

        // Button and Observe on isFiltered()
        viewModel.isFiltered.asLiveData().observe(this){isFiltered ->
            binding.buttonRecap.text = if (isFiltered)"Lista completa" else "Pizze ordinate"
        }
        binding.buttonRecap.setOnClickListener {
            viewModel.recapPizza()
        }
    }
}