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

    // Repositories
    private val pokemonRepository = PokemonRepository()
    private val firebaseRepository = FirebaseRepository()
    private val authFirebase = AuthFirebase()

    // LiveData
    val saved = MutableLiveData<Boolean>()
    val pokemonList = MutableLiveData<List<PokemonItem>>()
    val pokemonFavorities = MutableLiveData<List<PokemonItem>>()
    val pokemonDetail = MutableLiveData<PokemonDetail>()
    val pokemonDescription = MutableLiveData<PokemonDetail>()
    val apiError = MutableLiveData<Throwable>()
    val signOutLiveData = MutableLiveData<Boolean>()

    // Pagination
    private var currentPage = 0
    private val itemsPerPage = 10

    // Fetching data
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

    // Firebase operations
    fun savePokemonToFirestore(name: String) {
        firebaseRepository.savePokemonToFirestore(name).observeForever {
            println("Pokemon guardado correctamente.")
        }
    }

    fun getPokemonFromFirestore() {
        firebaseRepository.getPokemon().observeForever {
            pokemonFavorities.value = it
        }
    }

    fun isSavedPokemon(name: String) {
        firebaseRepository.isPokemonSaved(name).observeForever {
            saved.value = it
        }
    }

    // Authentication
    fun signOut(): MutableLiveData<Boolean> {
        authFirebase.signOut().observeForever {
            signOutLiveData.value = it
        }
        return signOutLiveData
    }

    // Pagination
    fun loadMorePokemons() {
        currentPage++
        fetchPokemonList()
    }
}