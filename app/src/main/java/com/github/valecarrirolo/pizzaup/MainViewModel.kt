package com.github.valecarrirolo.pizzaup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {
    private val _pizzas = MutableStateFlow<List<PizzaDetail>>(emptyList())
    val pizzas = _pizzas.asStateFlow()

    private val devClient = DevService.create()

    init {
        getPizza()
    }

    fun getPizza() {
        viewModelScope.launch {
            try {
                _pizzas.value = devClient.getPizza().pizzas
            } catch (e: IOException) {
                println("getPizza() fail: $e")
            }
        }
    }
}