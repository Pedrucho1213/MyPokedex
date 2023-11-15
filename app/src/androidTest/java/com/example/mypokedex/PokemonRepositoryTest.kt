package com.example.mypokedex

import com.example.mypokedex.api.PokemonService
import com.example.mypokedex.model.PokemonList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonServiceTest {

    @Mock
    private lateinit var mockService: PokemonService

    @Test
    fun testGetPokemonList() = runBlocking {
        // Define a dummy PokemonList
        val mockPokemonList = PokemonList(0, null, null, emptyList())

        // Establish the expected responses of the simulated methods
        Mockito.`when`(mockService.getPokemonList(0, 20)).thenReturn(mockPokemonList)

        // Call the method we are testing
        val returnedPokemonList = mockService.getPokemonList(0, 20)

        // Verify that the method was called with the correct parameters
        Mockito.verify(mockService).getPokemonList(0, 20)

        // Verify that the answers are the same as the simulated ones.
        Assert.assertEquals(mockPokemonList, returnedPokemonList)
    }
}