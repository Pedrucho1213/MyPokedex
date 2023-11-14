package com.example.mypokedex.api

import com.example.mypokedex.model.PokemonDetail
import com.example.mypokedex.model.PokemonList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("offset") offset : Int, @Query("limit") limit: Int): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetail

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonDetail

}