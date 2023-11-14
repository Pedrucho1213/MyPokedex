package com.example.mypokedex.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.api.PokemonRepository
import com.example.mypokedex.firebase.AuthFirebase
import com.example.mypokedex.model.PokemonDetail
import com.example.mypokedex.model.PokemonItem
import kotlinx.coroutines.launch

class PokemonViewModel: ViewModel() {

    private val pokemonRepository = PokemonRepository()
    private val session = AuthFirebase()
    private val signOut = MutableLiveData<Boolean>()



    val pokemonList = MutableLiveData<List<PokemonItem>>()
    val pokemonDetail = MutableLiveData<PokemonDetail>()
    val apiError = MutableLiveData<Throwable>()

    fun fetchPokemonList(offset: Int = 0, limit : Int = 20){
        viewModelScope.launch {
            try {
                val result = pokemonRepository.getPokemonList(offset, limit)
                pokemonList.value = result.results
            }catch (error: Throwable){
                apiError.value = error
            }
        }
    }

    fun fetchPokemonDetail(name: String){
        viewModelScope.launch {
            try {
                val result = pokemonRepository.getPokemonDetail(name)
                pokemonDetail.value = result
            }catch (error: Throwable){
                apiError.value = error
            }
        }
    }

    fun signOut(): MutableLiveData<Boolean> {
        session.sigOut().observeForever {
            signOut.value = it
        }
        return signOut
    }

}