package com.github.valecarrirolo.pizzaup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val _data = MutableStateFlow<String>("Test")
    val data = _data.asStateFlow()

    private val _person = MutableStateFlow<Person?>(null)
    val person = _person.asStateFlow()

    private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
    val quotes = _quotes.asStateFlow()

    private val mainClient = MainService.create()
    private val devClient = DevService.create()

    init {
        getQuote()
    }

    fun doSomething() {
        _data.value = Random.nextInt().toString()
    }

    fun randomPerson() {
        viewModelScope.launch {
            try {
                _person.value = mainClient.getPerson().person
            } catch (e: IOException) {
                println("randomPerson() fail: $e")
            }
        }
    }

    fun getQuote() {
        viewModelScope.launch {
            try {
                _quotes.value = devClient.getQuote()
                    .flatMap { quoteResponse ->
                        quoteResponse.quotes.map {
                            Quote(
                                author = quoteResponse.author,
                                text = it
                            )
                        }
                    }
                    .shuffled()
                    .take(5)
            } catch (e: IOException) {
                println("getQuote() fail: $e")
            }
        }
    }
}

data class Quote(val author: String, val text: String)