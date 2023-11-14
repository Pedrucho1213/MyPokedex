package com.example.mypokedex.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mypokedex.databinding.ActivityMainBinding
import com.example.mypokedex.viewModel.PokemonViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PokemonViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}