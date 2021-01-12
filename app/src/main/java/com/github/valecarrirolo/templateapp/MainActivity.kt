package com.github.valecarrirolo.templateapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import coil.load
import com.github.valecarrirolo.templateapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe data
        viewModel.data.asLiveData().observe(this) { data ->
            binding.textExample.text = data
            // Image loading
            binding.imageExample.load("https://robohash.org/$data")
        }

        // Observe person
        viewModel.person.asLiveData().observe(this) { person ->
            binding.personExample.text = if (person == null) {
                "Nessuna persona"
            } else {
                """
             |Name: ${person.personal.name}
             |Last name: ${person.personal.last_name}
             |Age: ${person.personal.age}
             |Job: ${person.work.position}""".trimMargin()
            }
        }

        // Fun when button clicked
        binding.buttonExample.setOnClickListener {
            viewModel.doSomething()
            viewModel.randomPerson()
        }

        // RecyclerView List

        val adapter = MainAdapter()
        binding.recyclerViewExample.adapter = adapter
        viewModel.quotes.asLiveData().observe(this) { quoteList ->
            adapter.dataSet = quoteList
            adapter.notifyDataSetChanged()
        }

    }
}