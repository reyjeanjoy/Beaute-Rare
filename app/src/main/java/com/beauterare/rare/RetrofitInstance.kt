package com.beauterare.rare

import com.beauterare.rare.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8000/" // Replace with your PHP server URL

    // Retrofit instance
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build()
    }

    // Expose the ApiService
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
