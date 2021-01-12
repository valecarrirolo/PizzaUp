package com.github.valecarrirolo.templateapp

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET

interface DevService {

    @GET("quotes.json")
    suspend fun getQuote(): List<QuoteResponse>

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
data class QuoteResponse (
    val author: String,
    val quotes: List<String>
)