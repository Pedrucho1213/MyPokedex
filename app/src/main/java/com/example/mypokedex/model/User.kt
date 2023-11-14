package com.example.mypokedex.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("uid") val uid: String? = null,
    @SerializedName("fullName") val fullName: String? = null,
    @SerializedName("email") val email: String? = null
)
