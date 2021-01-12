package com.github.valecarrirolo.templateapp

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET

interface MainService {

    @GET("getPerson")
    suspend fun getPerson(): PersonResponse

    companion object {
        fun create(): MainService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory(MediaType.parse("application/json")!!))
                .baseUrl("https://pipl.ir/v1/")
                .build()

            return retrofit.create(MainService::class.java)
        }
    }
}

@Serializable
data class PersonResponse (
    val person: Person
)

@Serializable
data class Person (
    val personal: Personal,
    val work: Work
)

@Serializable
data class Personal (
    val age: Long,
    val city: String,
    val last_name: String,
    val name: String
)

@Serializable
data class Work (
    val position: String
)