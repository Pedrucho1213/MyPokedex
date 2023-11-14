package com.example.mypokedex.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mypokedex.databinding.ActivitySignInBinding
import com.example.mypokedex.viewModel.PokemonViewModel

class SignInActivity : AppCompatActivity() {

    private val viewModel: PokemonViewModel by viewModels()
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}