package com.aneeq.starwarsplanets.domain.repository

import com.aneeq.starwarsplanets.domain.model.Planet
import com.aneeq.starwarsplanets.domain.model.PlanetResponse
import com.aneeq.starwarsplanets.domain.remote.APIService
import com.aneeq.starwarsplanets.utils.Logger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class PlanetRepositoryTest {

    @Mock
    private lateinit var apiService: APIService

    @Mock
    private lateinit var logger: Logger

    private lateinit var repository: PlanetRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = PlanetRepository(apiService, logger)
    }

    @Test
    fun getPlanetsReturnsValidData() = runTest {
        val mockPlanets = listOf(
            Planet(
                name = "Tatooine",
                climate = "arid",
                population = "200000",
                diameter = "10465",
                gravity = "1 standard",
                terrain = "desert"
            ),
            Planet(
                name = "Hoth",
                climate = "frozen",
                population = "unknown",
                diameter = "7200",
                gravity = "1.1 standard",
                terrain = "tundra"
            )
        )

        val mockResponse = PlanetResponse(
            count = mockPlanets.size,
            next = null,
            previous = null,
            results = mockPlanets
        )

        whenever(apiService.getPlanets(page = 1)).thenReturn(mockResponse)

        val result = repository.getPlanets(page = 1)
        val response = result.getOrThrow()

        assertEquals(2, response.results.size)
        assertEquals("Tatooine", response.results.first().name)
        Mockito.verify(apiService).getPlanets(page = 1)
    }

    @Test
    fun getPlanetsHandlesEmptyResponse() = runTest {
        val emptyResponse = PlanetResponse(
            count = 0,
            next = null,
            previous = null,
            results = emptyList()
        )

        whenever(apiService.getPlanets(page = 1)).thenReturn(emptyResponse)

        val result = repository.getPlanets(page = 1)
        val response = result.getOrThrow()

        assertTrue(response.results.isEmpty())
    }
}
