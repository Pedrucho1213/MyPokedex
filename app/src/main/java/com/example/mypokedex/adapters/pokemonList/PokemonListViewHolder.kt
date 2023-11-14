package com.example.mypokedex.adapters.pokemonList

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.databinding.CardPokemonBinding
import com.example.mypokedex.model.PokemonItem
import com.example.mypokedex.view.DetailActivity

class PokemonListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var binding = CardPokemonBinding.bind(view)

    fun bindView(pokemon: PokemonItem, context: Context) {
        binding.pokemonNameTxt.text = pokemon.name

        itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("name", pokemon.name)
            context.startActivity(intent)
        }
    }
}