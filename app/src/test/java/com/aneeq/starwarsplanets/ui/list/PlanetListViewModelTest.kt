package com.aneeq.starwarsplanets.ui.list

import com.aneeq.starwarsplanets.domain.model.Planet
import com.aneeq.starwarsplanets.domain.model.PlanetResponse
import com.aneeq.starwarsplanets.domain.repository.PlanetRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class FakePlanetRepository : PlanetRepositoryTestDouble() {
    override suspend fun getPlanets(page: Int): Result<PlanetResponse> {
        val planets = listOf(
            Planet("Tatooine", "arid", "200000", "10465", "1 standard", "desert")
        )
        val response = PlanetResponse(1, null, null, planets)
        return Result.success(response)
    }
}

// A simple abstract test double you can keep in test sources
abstract class PlanetRepositoryTestDouble {
    abstract suspend fun getPlanets(page: Int): Result<PlanetResponse>
}

class PlanetListViewModelSimpleTest {

    @Test
    fun fetchNextPage_setsSuccessState() = runBlocking {
        val repository = object : PlanetRepositoryTestDouble() {
            override suspend fun getPlanets(page: Int): Result<PlanetResponse> {
                val planets = listOf(Planet("Alderaan", "temperate", "2000000000", "12500", "1 standard", "grasslands"))
                val response = PlanetResponse(1, null, null, planets)
                return Result.success(response)
            }
        }

        val viewModel = PlanetListViewModel(repository as PlanetRepository)

        viewModel.fetchNextPage()

        val state = viewModel.uiState
        assertTrue(state is PlanetUiState.Success)
        val planets = (state as PlanetUiState.Success).planets
        assertEquals("Alderaan", planets.first().name)
    }
}
