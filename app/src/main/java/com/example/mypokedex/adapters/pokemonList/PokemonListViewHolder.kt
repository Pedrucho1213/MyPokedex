package com.example.mypokedex.adapters.pokemonList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.databinding.CardPokemonBinding
import com.example.mypokedex.model.PokemonItem

class PokemonListViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var binding = CardPokemonBinding.bind(view)

    fun bindView(pokemon: PokemonItem) {
        binding.pokemonNameTxt.text = pokemon.name
    }
}