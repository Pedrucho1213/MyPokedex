package com.example.mypokedex.localStorage


import android.content.Context
import androidx.preference.PreferenceManager

object PreferenceManager {

    private const val KEY_NAME = "fullName"
    private const val KEY_UID = "uid"
    private const val KEY_EMAIL = "email"
    private const val KEY_LOGGED = "isLoggedIn"


    fun setLogIn(context: Context, logged: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_LOGGED, logged)
        editor.apply()
    }

    fun getLogIn(context: Context): Boolean{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(KEY_LOGGED, false)
    }

    fun saveName(context: Context, name: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, name)
        editor.apply()
    }

    fun saveEmail(context: Context, email: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }

    fun saveUID(context: Context, uid: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_UID, uid)
        editor.apply()
    }

    fun getName(context: Context): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun getEmail(context: Context): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    fun getUID(context: Context): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(KEY_UID, null)
    }

    fun logOut(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_UID)
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_NAME)
        editor.remove(KEY_LOGGED)
        editor.apply()
        editor.clear()

    }


}