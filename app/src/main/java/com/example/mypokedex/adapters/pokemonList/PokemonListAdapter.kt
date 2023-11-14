package com.example.mypokedex.adapters.pokemonList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.R
import com.example.mypokedex.model.PokemonItem

class PokemonListAdapter(private var pokemonList: List<PokemonItem>, private val context: Context) :
    RecyclerView.Adapter<PokemonListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_pokemon, parent, false)
        return PokemonListViewHolder(view)
    }

    override fun getItemCount(): Int = pokemonList.size

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bindView(pokemon, context)
    }

    fun updateData(filteredPokemons: List<PokemonItem>) {
        pokemonList = filteredPokemons
        notifyDataSetChanged()
    }

}