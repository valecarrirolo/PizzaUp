package com.github.valecarrirolo.pizzaup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {
    private val _allPizzas = MutableStateFlow<List<NumPizzaDetail>>(emptyList())
    val allPizzas = _allPizzas.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    val orderedPizzas = allPizzas.map { allPizzas ->
        allPizzas.filter { it.num > 0 }
    }
    val isPizzasEmpty = combine(orderedPizzas, isLoading) { filteredPizza, isLoading ->
        filteredPizza.isEmpty() && !isLoading
    }

    val totalCost = orderedPizzas.map { orderedPizzas ->
        orderedPizzas.sumOf { it.price * it.num }
    }

    //    private val yeelight = YeelightManager()
    private val devClient = DevService.create()

    init {
        getPizza()
        _allPizzas.onEach { allPizzas ->
            allPizzas.firstOrNull() {
                it.name.lowercase() == "margherita"
            }?.also { margherita ->
                // ?.also non chiama also se l'oggetto precedente è null
//                redLightOn(margherita)
            }

        }.launchIn(viewModelScope)
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

//    Swicth pulsante 1 versione
//    fun recapPizza() {
//        _isFiltered.value = !_isFiltered.value
//    }

//    //LightOn only in local WiFi - Try-catch exception
//    fun redLightOn(item: NumPizzaDetail) {
//        // NetworkOnMainThreadException -> coroutine non può funzionare sul main thread e va spostata con (context = Dispatchers.IO)
//        viewModelScope.launch {
//            try {
//                if (item.name.toLowerCase() == "margherita") {
//              if (item.num >= 1) {
//                        yeelight.camera1().setPower(true)
//                        yeelight.camera1().setColorRgb(0xFF0000)
//                    } else {
//                        yeelight.camera1().setWhiteTemperature(5000)
//                    }
//                }
//            } catch (e: Exception) {
//                Log.w("yeelight", "yeelight.studio1() not found?", e)
//            }
//        }
//    }
}

data class NumPizzaDetail(
    val name: String,
    val photo: String,
    val price: Double,
    val ingredients: List<String>,
    val num: Int
)