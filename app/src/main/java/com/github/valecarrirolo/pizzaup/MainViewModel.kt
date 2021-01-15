package com.github.valecarrirolo.pizzaup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {
    private val _pizzas = MutableStateFlow<List<NumPizzaDetail>>(emptyList())
    val pizzas = _pizzas.asStateFlow()


    private val devClient = DevService.create()

    init {
        getPizza()
    }

    fun getPizza() {
        viewModelScope.launch {
            try {
                _pizzas.value = devClient.getPizza().pizzas.map {
                    NumPizzaDetail(
                        it.name,
                        it.price,
                        it.ingredients,
                        0
                    )
                }
            } catch (e: IOException) {
                println("getPizza() fail: $e")
            }
        }
    }

    fun addPizza(item: NumPizzaDetail) {
        _pizzas.value = _pizzas.value.map {
            if (it == item) {
                it.copy(num = it.num + 1)
            } else {
                it
            }
        }
    }

    fun removePizza(item: NumPizzaDetail) {
        _pizzas.value = _pizzas.value.map {
            if(it == item && it.num != 0){
                it.copy(num = it.num -1)
            }else{
                it
            }
        }

    }
}

data class NumPizzaDetail(
    val name: String,
    val price: Double,
    val ingredients: List<String>,
    val num: Int
)