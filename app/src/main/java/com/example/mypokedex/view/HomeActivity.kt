package com.example.mypokedex.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypokedex.R
import com.example.mypokedex.adapters.pokemonList.PokemonListAdapter
import com.example.mypokedex.databinding.ActivityHomeBinding
import com.example.mypokedex.localStorage.PreferenceManager
import com.example.mypokedex.model.PokemonItem
import com.example.mypokedex.viewModel.PokemonViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: PokemonListAdapter
    private var pokemons = mutableListOf<PokemonItem>()
    private val viewModel by lazy {
        ViewModelProvider(this)[PokemonViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleInternetConnection()
        setEvents()
        initRecyclerView()
        getAllData()
    }


    private fun getFavorites() {
        pokemons.clear()
    }

    private fun getAllData() {
        pokemons.clear()
        viewModel.fetchPokemonList(0, 3)
        viewModel.pokemonList.observe(this) { pokemonList ->
            pokemons = pokemonList.toMutableList()
            adapter.updateData(pokemons)
            initRecyclerView()
        }
    }

    private fun initRecyclerView() {
        adapter = PokemonListAdapter(pokemons, context = this)
        binding.pokemonsRv.layoutManager = LinearLayoutManager(this)
        binding.pokemonsRv.adapter = adapter
    }

    private fun setEvents() {
        binding.searchTxt.setOnQueryTextListener(this)
        binding.searchTxt.setIconifiedByDefault(false)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.pokemons_btn -> handlePokemonsButton()
                R.id.favorites_btn -> handleMyFavoritesButton()
                R.id.logout_btn -> handleLogoutButton()
                else -> false
            }
        }
        binding.rootLayout.setOnClickListener {
            hideKeyboard()
            binding.searchTxt.clearFocus()
            currentFocus?.clearFocus()
        }
    }

    private fun handleMyFavoritesButton(): Boolean {
        getFavorites()
        return true
    }

    private fun handlePokemonsButton(): Boolean {
        getAllData()
        return true
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun handleInternetConnection() {
        if (!isNetworkAvailable()) {
            showNoInternetMessage()
        }
    }

    private fun showNoInternetMessage() {
        Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

    }

    private fun handleLogoutButton(): Boolean {
        showLogoutConfirmDialog()
        return true
    }

    private fun showLogoutConfirmDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title_dialog))
            .setMessage(resources.getString(R.string.body_dialog))

            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->

            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                performLogout()
            }
            .show()
    }

    private fun performLogout() {
        viewModel.signOut().observe(this) {
            if (it) {
                PreferenceManager.logOut(this)
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

}