package com.beauterare.rare

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://http://192.168.1.9/") // Base URL of your API
                .addConverterFactory(GsonConverterFactory.create()) // Convert JSON to Kotlin objects
                .build()
        }
        return retrofit!!
    }
}