package com.example.mypokedex.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "uid")             var uid: String? = null,
    @Json(name = "fullName")   var fullName: String? = null,
    @Json(name = "email")         var email: String? = null,
)
