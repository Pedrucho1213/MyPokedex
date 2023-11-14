package com.example.mypokedex.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    private const val AUTH_TOKEN = ""

    fun getRetrofit(): PokemonService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
    }
}