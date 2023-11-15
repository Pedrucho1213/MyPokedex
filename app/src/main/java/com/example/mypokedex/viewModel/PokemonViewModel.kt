package com.example.mypokedex.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.api.PokemonRepository
import com.example.mypokedex.firebase.AuthFirebase
import com.example.mypokedex.firebase.FirebaseRepository
import com.example.mypokedex.model.PokemonDetail
import com.example.mypokedex.model.PokemonItem
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    private val pokemonRepository = PokemonRepository()
    private val firebase = FirebaseRepository()
    private val session = AuthFirebase()
    private val signOut = MutableLiveData<Boolean>()

    val saved = MutableLiveData<Boolean>()
    val pokemonList = MutableLiveData<List<PokemonItem>>()
    val pokemonFavorities = MutableLiveData<List<PokemonItem>>()
    val pokemonDetail = MutableLiveData<PokemonDetail>()
    val pokemonDescription = MutableLiveData<PokemonDetail>()
    val apiError = MutableLiveData<Throwable>()

    private var currentPage = 0
    private val itemsPerPage = 10

    fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                val offset = currentPage * itemsPerPage
                val result = pokemonRepository.getPokemonList(offset, itemsPerPage)
                pokemonList.value = result.results
            } catch (error: Throwable) {
                apiError.value = error
            }
        }
    }

    fun fetchPokemonDetail(name: String) {
        viewModelScope.launch {
            try {
                val result = pokemonRepository.getPokemonDetail(name)
                pokemonDetail.value = result
            } catch (error: Throwable) {
                apiError.value = error
            }
        }
    }

    fun fetchDescription(id: Int) {
        viewModelScope.launch {
            try {
                val result = pokemonRepository.getDescription(id)
                pokemonDescription.value = result
            } catch (error: Throwable) {
                apiError.value = error
            }
        }
    }

    fun searchPokemon(name: String) {
        viewModelScope.launch {
            try {
                val result = pokemonRepository.getPokemonDetail(name)
                pokemonDetail.value = result
            } catch (error: Throwable) {
                apiError.value = error
            }
        }
    }

    fun savePokemonToFireStore(name: String) {
        firebase.savePokemonToFireStore(name).observeForever {
            println("Pokemon guardado correctamente.")
        }
    }

    fun getPokemonFromFireStore(){
        firebase.getPokemon().observeForever {
            pokemonFavorities.value = it
        }
    }

    fun isSavedPokemon(name: String){
        firebase.isPokemonSaved(name).observeForever {
            saved.value = it
        }
    }

    fun signOut(): MutableLiveData<Boolean> {
        session.sigOut().observeForever {
            signOut.value = it
        }
        return signOut
    }

    fun loadMorePokemons() {
        currentPage++
        fetchPokemonList()
    }


}