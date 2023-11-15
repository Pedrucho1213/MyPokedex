package com.example.mypokedex.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mypokedex.model.PokemonItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private val mutableData = MutableLiveData<MutableList<PokemonItem>>()


    fun savePokemonToFireStore(name: String): LiveData<MutableList<PokemonItem>> {
        val session = auth.currentUser?.email
        if (session != null) {
            // Check if the name already exists in the collection
            fireStore.collection("Pokemons")
                .whereEqualTo("name", name)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        val pokemon = hashMapOf(
                            "name" to name,
                            "email" to session
                        )
                        fireStore.collection("Pokemons")
                            .add(pokemon)
                            .addOnSuccessListener { documentReference ->
                                getPokemon()
                                println("Pokemon saved successfully. ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { exception ->
                                println("Failed to save the Pokemon: ${exception.message}")
                            }
                    } else {
                        println("The name already exists.")
                    }
                }
                .addOnFailureListener { exception ->
                    println("Failed to verify the Pokemon name: ${exception.message}")
                }
        } else {
            println("Failed to get user's email.")
        }
        return mutableData
    }

    fun getPokemon(): LiveData<MutableList<PokemonItem>> {
        val session = auth.currentUser?.email
        fireStore.collection("Pokemons")
            .whereEqualTo("email", session)
            .get()
            .addOnSuccessListener { documents ->
                val listPokemons = mutableListOf<PokemonItem>()
                for (document in documents) {
                    val name = document.getString("name")
                    val pokemon = PokemonItem(name.toString(), "saved")
                    listPokemons.add(pokemon)
                }
                mutableData.value = listPokemons
            }
        return mutableData
    }

    fun isPokemonSaved(name: String): LiveData<Boolean> {
        val session = auth.currentUser?.email

        if (session != null) {
            val query = fireStore.collection("Pokemons")
                .whereEqualTo("name", name)
                .whereEqualTo("email", session)
                .limit(1)

            val mutableData = MutableLiveData<Boolean>()

            query.get()
                .addOnSuccessListener { documents ->
                    mutableData.value = !documents.isEmpty
                }
                .addOnFailureListener { exception ->
                    println("Error executing the query: ${exception.message}")
                    mutableData.value = false
                }

            return mutableData
        } else {
            println("Failed to get user's email.")
            return MutableLiveData<Boolean>().apply { value = false }
        }
    }


}