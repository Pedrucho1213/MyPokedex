package com.example.mypokedex.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mypokedex.R
import com.example.mypokedex.databinding.ActivitySignInBinding
import com.example.mypokedex.localStorage.PreferenceManager
import com.example.mypokedex.viewModel.SignInViewModel

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[SignInViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
            verifyLogin()
            setListeners()
    }
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun verifyLogin() {
        val isLoggedIn = PreferenceManager.getLogIn(this)
        if (isLoggedIn) {
            redirectToHomePage()
        }
    }

    private fun redirectToHomePage() {
        val welcomeMessage = getString(R.string.welcome_message, PreferenceManager.getName(this))
        Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setListeners() {

        binding.signUpBtn.setOnClickListener {
            val i = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(i)
        }

        binding.loginBtn.setOnClickListener {
            performLogin()
        }

        binding.rootLayout.setOnClickListener {

            hideKeyboard()

            binding.emailTxt.editText?.clearFocus()
            binding.passwordTxt.editText?.clearFocus()

            currentFocus?.clearFocus()

        }
    }

    private fun performLogin() {
        val email = binding.emailTxt.editText?.text.toString()
        val pass = binding.passwordTxt.editText?.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            viewModel.loginWithEmail(email, pass).observe(this) { result ->
                if (result.isSuccessful) {
                    PreferenceManager.setLogIn(this, true)
                    getUserData(email)
                } else {
                    Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getUserData(email: String) {
        viewModel.getCredentials(email).observe(this) { user ->
            if (user != null) {
                PreferenceManager.saveUID(this, user.uid.toString())
                PreferenceManager.saveName(this, user.fullName.toString())
                PreferenceManager.saveEmail(this, email)
                redirectToHomePage()
            }
        }
    }
}