package com.github.valecarrirolo.pizzaup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {
    private val _allPizzas = MutableStateFlow<List<NumPizzaDetail>>(emptyList())
    // val allPizzas = _allPizzas.asStateFlow()

    private val _isFiltered = MutableStateFlow<Boolean>(false)
    val isFiltered = _isFiltered.asStateFlow()

    val currentPizzas = combine(_allPizzas, _isFiltered) { allPizzas, isFiltered ->
        if (isFiltered) allPizzas.filter { it.num > 0 } else allPizzas
    }

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    val isPizzasEmpty = combine(currentPizzas, isLoading) { filteredPizza, isLoading ->
        filteredPizza.isEmpty() && !isLoading
    }

    private val devClient = DevService.create()

    init {
        getPizza()
    }

    fun getPizza() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _allPizzas.value = devClient.getPizza().pizzas.map {
                    NumPizzaDetail(
                        it.name,
                        it.photo,
                        it.price,
                        it.ingredients,
                        0
                    )
                }
                _isLoading.value = false
            } catch (e: IOException) {
                println("getPizza() fail: $e")
                _isLoading.value = false
            }
        }
    }

    fun addPizza(item: NumPizzaDetail) {
        _allPizzas.value = _allPizzas.value.map {
            if (it == item && item.num < 20) {
                it.copy(num = it.num + 1)
            } else {
                it
            }
        }
    }

    fun removePizza(item: NumPizzaDetail) {
        _allPizzas.value = _allPizzas.value.map {
            if (it == item && it.num != 0) {
                it.copy(num = it.num - 1)
            } else {
                it
            }
        }
    }

    fun recapPizza() {
        _isFiltered.value = !_isFiltered.value
    }

   fun redLightOn(){

   }
}

data class NumPizzaDetail(
    val name: String,
    val photo: String,
    val price: Double,
    val ingredients: List<String>,
    val num: Int
)