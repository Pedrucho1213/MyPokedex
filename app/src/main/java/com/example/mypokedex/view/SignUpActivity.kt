package com.example.mypokedex.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mypokedex.databinding.ActivitySignUpBinding
import com.example.mypokedex.localStorage.PreferenceManager
import com.example.mypokedex.model.User
import com.example.mypokedex.viewModel.SingUpViewModel
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[SingUpViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }


    private fun setListeners() {
        binding.signUpBtn.setOnClickListener {
            validateInputs()
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.rootLayout.setOnClickListener {
            hideKeyboard()
            binding.emailTxt.editText?.clearFocus()
            binding.fullNameTxt.editText?.clearFocus()
            binding.passwordTxt.editText?.clearFocus()
            binding.confirmPassTxt.editText?.clearFocus()

            currentFocus?.clearFocus()
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

    }


    private fun registerUser() {
        val email = binding.emailTxt.editText?.text.toString()
        val pass = binding.confirmPassTxt.editText?.text.toString()
        viewModel.registerWithEmail(email, pass).observe(this) { result ->
            if (result.isSuccessful) {
                saveUserData(result.result.user?.uid.toString())
                makeToast("Registered user")
            } else {
                handleRegistrationError(result.exception)
            }
        }
    }

    private fun handleRegistrationError(exception: Exception?) {
        when (exception) {
            is FirebaseAuthInvalidUserException -> {
                makeToast("The user is already registered")
                Log.i("Error", "The user is already registered")
            }

            else -> {
                makeToast("An error has occurred")
                Log.i("Error", "An error has occurred ${exception.toString()}")
            }
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    private fun saveUserData(uid: String) {
        val email = binding.emailTxt.editText?.text.toString()
        val fullName = binding.fullNameTxt.editText?.text.toString()
        val user = User(uid, fullName, email)
        viewModel.saveUserData(user).observe(this) { result ->
            if (result.isSuccessful) {
                saveUserPreferences(uid, fullName, email)
                navigateToSignInActivity()
            }
        }
    }

    private fun saveUserPreferences(uid: String, fullName: String, email: String) {
        PreferenceManager.saveUID(this, uid)
        PreferenceManager.saveName(this, fullName)
        PreferenceManager.saveEmail(this, email)
    }

    private fun navigateToSignInActivity() {
        val intent = Intent(applicationContext, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateInputs() {
        val email = binding.emailTxt.editText?.text.toString()
        val fullName = binding.fullNameTxt.editText?.text.toString()
        val pass = binding.passwordTxt.editText?.text.toString()
        val confirmPass = binding.confirmPassTxt.editText?.text.toString()

        if (email.isEmpty()) {
            binding.emailTxt.error = "Please enter an email"
        } else if (!isValidEmail(email)) {
            binding.emailTxt.error = "Please enter a valid email"
        } else {
            binding.emailTxt.error = null
        }

        if (fullName.isEmpty()) {
            binding.fullNameTxt.error = "Please enter your full name"
        } else {
            binding.fullNameTxt.error = null
        }

        if (pass.isEmpty()) {
            binding.confirmPassTxt.error = "Please enter a password"
        } else if (pass.length < 6) {
            binding.confirmPassTxt.error = "Password must be at least 6 characters long"
        } else {
            binding.confirmPassTxt.error = null
        }

        if (confirmPass.isEmpty()) {
            binding.confirmPassTxt.error = "Please confirm your password"
        } else if (pass != confirmPass) {
            binding.confirmPassTxt.error = "Passwords do not match"
        } else {
            binding.confirmPassTxt.error = null
        }

        if (binding.emailTxt.error == null && binding.fullNameTxt.error == null && binding.confirmPassTxt.error == null) {
            registerUser()
        }
    }


    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}