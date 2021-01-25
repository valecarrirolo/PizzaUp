package com.github.valecarrirolo.pizzaup

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url

interface DevService {

    @GET("pizzas.json")
    suspend fun getPizza(): PizzaResponse

    companion object {
        fun create(): DevService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory(MediaType.parse("application/json")!!))
                .baseUrl("https://raw.githubusercontent.com/nemsi85/dev-server/master/")
                .build()

            return retrofit.create(DevService::class.java)
        }
    }
}

@Serializable
data class PizzaResponse(
    val pizzas : List<PizzaDetail>
)

@Serializable
data class PizzaDetail (
    val name: String,
    val price: Double,
    val photo: String,
    val ingredients: List<String>
)