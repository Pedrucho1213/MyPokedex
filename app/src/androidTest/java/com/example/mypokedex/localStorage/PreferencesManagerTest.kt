package com.example.mypokedex.localStorage

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class PreferencesManagerTest {

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var editor: SharedPreferences.Editor

    @Mock
    lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(context.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(sharedPreferences)
        Mockito.`when`(sharedPreferences.edit()).thenReturn(editor)

        // Imaginemos que tenemos una clase `PreferenceManager.getDefaultSharedPreferences()` personalizada y establezcamos el comportamiento del mock
        Mockito.`when`(PreferenceManager.getDefaultSharedPreferences(context)).thenReturn(sharedPreferences)
    }

    @Test
    fun testPreferencesManager() {
        PreferencesManager.setLogIn(context, true)
        PreferencesManager.saveName(context, "Test")
        PreferencesManager.saveEmail(context, "test@test.com")
        PreferencesManager.saveUID(context, "12345")

        Mockito.verify(editor, Mockito.times(1)).putBoolean(PreferencesManager.KEY_LOGGED, true)
        Mockito.verify(editor, Mockito.times(1)).putString(PreferencesManager.KEY_NAME, "Test")
        Mockito.verify(editor, Mockito.times(1)).putString(PreferencesManager.KEY_EMAIL, "test@test.com")
        Mockito.verify(editor, Mockito.times(1)).putString(PreferencesManager.KEY_UID, "12345")

        Mockito.`when`(sharedPreferences.getBoolean(PreferencesManager.KEY_LOGGED, false)).thenReturn(true)
        Mockito.`when`(sharedPreferences.getString(PreferencesManager.KEY_NAME, null)).thenReturn("Test")
        Mockito.`when`(sharedPreferences.getString(PreferencesManager.KEY_EMAIL, null)).thenReturn("test@test.com")
        Mockito.`when`(sharedPreferences.getString(PreferencesManager.KEY_UID, null)).thenReturn("12345")

        assert(PreferencesManager.getLogIn(context))
        assert(PreferencesManager.getName(context) == "Test")
        assert(PreferencesManager.getEmail(context) == "test@test.com")
        assert(PreferencesManager.getUID(context) == "12345")

        PreferencesManager.logOut(context)
        Mockito.verify(editor, Mockito.times(1)).remove(PreferencesManager.KEY_UID)
        Mockito.verify(editor, Mockito.times(1)).remove(PreferencesManager.KEY_EMAIL)
        Mockito.verify(editor, Mockito.times(1)).remove(PreferencesManager.KEY_NAME)
        Mockito.verify(editor, Mockito.times(1)).remove(PreferencesManager.KEY_LOGGED)
        Mockito.verify(editor, Mockito.times(1)).clear()
    }
}