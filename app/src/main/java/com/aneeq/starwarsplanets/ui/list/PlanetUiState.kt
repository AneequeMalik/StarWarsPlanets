package com.aneeq.starwarsplanets.ui.list

import com.aneeq.starwarsplanets.domain.model.Planet

sealed class PlanetUiState {
    object Loading : PlanetUiState()
    data class Success(val planets: List<Planet>) : PlanetUiState()
    data class Error(val message: String) : PlanetUiState()
}