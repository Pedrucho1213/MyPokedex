package com.example.mypokedex.api

import com.example.mypokedex.model.PokemonDetail
import com.example.mypokedex.model.PokemonList

class PokemonRepository {

    private val pokemonService = RetrofitBuilder.getRetrofit()
    suspend fun getPokemonList(offset: Int = 0, limit: Int = 20): PokemonList {
        return pokemonService.getPokemonList(offset, limit)
    }

    suspend fun getPokemonDetail(name: String): PokemonDetail {
        return pokemonService.getPokemonDetail(name)
    }

    suspend fun getDescription(name: String): PokemonDetail{
        return pokemonService.getPokemonSpecies(name)
    }
}