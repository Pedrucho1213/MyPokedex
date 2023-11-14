package com.example.mypokedex.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokedex.firebase.AuthFirebase
import com.example.mypokedex.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference

class SingUpViewModel : ViewModel() {

    private val repository = AuthFirebase()
    private val statusAuth = MutableLiveData<Task<AuthResult>>()
    private val userStatus = MutableLiveData<Task<DocumentReference>>()

    fun registerWithEmail(email: String, password: String): MutableLiveData<Task<AuthResult>> {
        repository.registerUserWithEmail(email, password).observeForever {
            statusAuth.value = it
        }
        return statusAuth
    }

    fun saveUserData(user: User): MutableLiveData<Task<DocumentReference>> {
        repository.saveUserData(user).observeForever {
            userStatus.value = it
        }
        return userStatus
    }
}